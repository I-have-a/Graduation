package com.bi.element.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bi.element.domain.po.*;
import com.bi.element.domain.vo.ItemVO;
import com.bi.element.mapper.ItemMapper;
import com.bi.element.mapper.UserEventOrderMapper;
import com.bi.element.service.EventInstancesService;
import com.bi.element.service.ItemService;
import com.bi.element.service.RecurrenceRuleDetailService;
import com.bi.element.service.RecurrenceRuleService;
import com.bi.element.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

    private final ItemMapper itemMapper;
    private final UserEventOrderMapper userEventOrderMapper;
    private final EventInstancesService eventInstancesService;
    private final RecurrenceRuleService recurrenceRuleService;
    private final RecurrenceRuleDetailService recurrenceRuleDetailService;

    @Override
    public Boolean deleteItem(Long id) {
        LambdaQueryWrapper<Item> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Item::getId, id);
        int flag = itemMapper.delete(wrapper);
        return flag == 1;
    }

    @Transactional
    @Override
    public Boolean addItem(ItemVO rootItemVO) {
        Long userid = SecurityUtils.getUserId();
        String username = SecurityUtils.getUsername();
        Item rootItemPO = new Item();
        BeanUtil.copyProperties(rootItemVO, rootItemPO);
        // 插入主事件
        int itemId = insertItem(rootItemPO, userid, username, 0);
        // 生成实例
        List<EventInstance> eventInstances = generateEventInstances(rootItemPO);
        // 插入排序结果
        insertOrder(userid, rootItemPO, eventInstances);

        return itemId != 0;
    }

    /**
     * 插入事件
     *
     * @param item     事件主体
     * @param userId   用户id
     * @param username 用户名
     * @param depth    事件深度
     * @return 事件id
     */
    private int insertItem(Item item, Long userId, String username, Integer depth) {
        item.setOwnerId(userId);
        item.setCreateBy(username);
        item.setUpdateBy(username);
        item.setDepth(depth);
        RecurrenceRule recurrenceType = item.getRecurrenceType();
        if (recurrenceType != null) {
            recurrenceRuleService.save(recurrenceType);
            recurrenceType.getDetails().forEach(detail -> {
                detail.setRuleId(recurrenceType.getId());
                recurrenceRuleDetailService.save(detail);
            });
            item.setRecurrenceRuleId(recurrenceType.getId());
        }
        if (item.getEventDate() == null) item.setEndDate(LocalDate.now());
        if (item.getStartDate() == null) item.setStartDate(LocalDate.now());
        int id = itemMapper.insert(item);
        if (item.getSubItems() == null || item.getSubItems().isEmpty()) return id;
        for (Item childItem : item.getSubItems()) {
            childItem.setParentId(item.getId());
            String path = item.getPath();
            childItem.setPath((path == null ? "" : path) + "/" + item.getId());
            insertItem(childItem, userId, username, depth + 1);
        }
        return id;
    }

    /**
     * 插入实例,并继承排序
     *
     * @param item 事件主体
     */
    public List<EventInstance> generateEventInstances(Item item) {
        if (item.getRecurrenceType() == null) return null;
        RecurrenceRule recurrenceType = item.getRecurrenceType();
        List<EventInstance> instances = new ArrayList<>();
        if (recurrenceType.getStartDate() == null) recurrenceType.setStartDate(item.getEventDate());
        for (LocalDate date : generateRecurringDates(recurrenceType)) {
            // 插入重复事件
            List<EventInstance> childInstances = new ArrayList<>();
            EventInstance eventInstance = new EventInstance();
            eventInstance.setItemId(item.getId());
            eventInstance.setEventDate(date);
            item.getSubItems().forEach(childItem -> {
                EventInstance childInstance = new EventInstance();
                childInstance.setItemId(childItem.getId());
                childInstance.setEventDate(date);
                childInstances.add(childInstance);
            });
            eventInstance.setSubInstances(childInstances);
            eventInstancesService.saveBatch(childInstances);
            instances.add(eventInstance);
        }
        eventInstancesService.saveBatch(instances);
        return instances;
    }

    public Page<Item> getEventsByDate(LocalDate date, int page, int size) {
        Page<Item> pageParam = new Page<>(page, size);
        return itemMapper.selectByDate(pageParam, date);
    }

    public List<LocalDate> generateRecurringDates(RecurrenceRule rule) {
        List<RecurrenceRuleDetail> details = rule.getDetails();
        List<LocalDate> dates = new ArrayList<>();

        LocalDate currentDate = rule.getStartDate();
        LocalDate endDate = rule.getEndDate();
        if (endDate == null) endDate = LocalDate.now().plusDays(90);

        while (!currentDate.isAfter(endDate)) {
            if (matchesRule(currentDate, details)) {
                dates.add(currentDate);
            }
            currentDate = currentDate.plusDays(1);
        }
        return dates;
    }

    private boolean matchesRule(LocalDate date, List<RecurrenceRuleDetail> details) {
        for (RecurrenceRuleDetail detail : details) {
            switch (detail.getType()) {
                case BY_WEEKLY:
                    String targetDay = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.CHINA).toUpperCase();
                    if (targetDay.equals(detail.getValue())) return true;
                    break;
                case BY_MONTH_DAY:
                    if (date.getDayOfMonth() == Integer.parseInt(detail.getValue())) return true;
                    break;
                case BY_YEAR_DAY:
                    if (date.getDayOfYear() == Integer.parseInt(detail.getValue())) return true;
                    break;
            }
        }
        return false;
    }

    private void insertOrder(Long userid, Item item, List<EventInstance> instances) {
        if (item.getRecurrenceType() == null) insertItemOrder(userid, item, null);
        else insertEventInstancesOrder(userid, instances, null);
    }

    private void insertItemOrder(Long userid, Item item, Long parentId) {
        Integer orderNum = userEventOrderMapper.selectMaxOrderNumByUser(userid, parentId, item.getEventDate());
        if (orderNum == null) orderNum = 0;
        UserEventOrder userEventOrder = new UserEventOrder();
        userEventOrder.setOrderNum(orderNum + 1);
        userEventOrder.setItemId(item.getId());
        userEventOrder.setUserId(userid);
        userEventOrder.setParentId(parentId);
        userEventOrderMapper.insert(userEventOrder);
        if (item.getSubItems() == null || item.getSubItems().isEmpty()) return;
        for (Item childItem : item.getSubItems()) {
            insertItemOrder(userid, childItem, item.getId());
        }
    }

    private void insertEventInstancesOrder(Long userId, List<EventInstance> instances, Long parentId) {
        for (EventInstance instance : instances) {
            Integer num = userEventOrderMapper.selectMaxOrderNumByUser(userId, parentId, instance.getEventDate());
            UserEventOrder order = new UserEventOrder();
            order.setUserId(userId);
            order.setEventInstanceId(instance.getId());
            order.setParentId(instance.getId()); // 继承父层级
            order.setOrderNum(num); // 继承父排序偏移
            insertEventInstancesOrder(userId, instance.getSubInstances(), instance.getId());
            userEventOrderMapper.insert(order);
        }
    }

    private void insertSharedUserOrders(Item item, List<Long> sharedUserIds) {
        for (Long userId : sharedUserIds) {
            Integer userMaxOrder = userEventOrderMapper.selectMaxOrderNumByUser(userId, null, item.getEventDate());
            int userOrderNum = (userMaxOrder == null) ? 1 : userMaxOrder + 1;

            UserEventOrder sharedOrder = new UserEventOrder();
            sharedOrder.setUserId(userId);
            sharedOrder.setItemId(item.getId());
            sharedOrder.setOrderNum(userOrderNum);
            userEventOrderMapper.insert(sharedOrder);
        }
    }

    public List<Item> getEventsByDateRange(LocalDateTime start, LocalDateTime end) {
        return itemMapper.selectByDateRange(start, end);
    }

    @Override
    public Boolean changeItem(ItemVO item) {
        return false;
    }

    @Override
    public List<Item> getItem(ItemVO item) {
        Long userId = SecurityUtils.getUserId();
        List<Item> rootItems = itemMapper.selectRootItems(userId);
        for (Item item1 : rootItems) {
            item1.setSubItems(getChildrenRecursive(userId, item1.getId()));
        }
        return rootItems;
    }

    private List<Item> getChildrenRecursive(Long userId, Long parentId) {
        List<Item> children = itemMapper.selectChildren(userId, parentId);
        for (Item child : children) {
            child.setSubItems(getChildrenRecursive(userId, child.getId()));
        }
        return children;
    }
}
