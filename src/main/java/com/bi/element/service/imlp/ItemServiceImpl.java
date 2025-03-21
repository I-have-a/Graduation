package com.bi.element.service.imlp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bi.element.domain.po.Item;
import com.bi.element.mapper.ItemMapper;
import com.bi.element.mapper.UserMapper;
import com.bi.element.response.Code;
import com.bi.element.response.R;
import com.bi.element.service.ItemService;
import com.bi.element.utils.RedisCache;
import com.bi.element.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final RedisCache redisCache;

    private final ItemMapper itemMapper;

    private final UserMapper userMapper;

    @Override
    public R deleteItem(Long id) {
        LambdaQueryWrapper<Item> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Item::getId, id);
        int flag = itemMapper.delete(wrapper);
        return flag == 1 ? new R("删除成功", true, Code.SUCCESS) : new R("删除失败", false, Code.FAIL);
    }

    @Transactional
    @Override
    public R addItem(Item item) {
        boolean b = true;
        String msg = "";
        if (item.getTitle() != null) {
            if (item.getTitle().trim().isEmpty()) {
                b = false;
                msg = "标题不能为空";
            }
        } else b = false;
        if (item.getTitle() != null) {
            if (item.getTitle().trim().length() >= 50) {
                b = false;
                msg = "标题长度不能大于50";
            }
        }
        if (item.getComplexity() != null) {
            if (item.getComplexity() > 2 || item.getComplexity() < 0) {
                b = false;
                msg = "复杂度不在范围";
            }
        } else b = false;

        if (!b) return new R(msg, false, Code.FAIL);
        Long userid = SecurityUtils.getUserId();
        int elementID = itemMapper.insert(item);
        if (elementID > 0) {
            if (itemMapper.addUE(userid, elementID) > 0)
                return new R("新建完成", true, Code.SUCCESS);
        }
        return new R("新建失败", false, Code.FAIL);
    }

    @Override
    public R changeItem(Item item) {
        boolean b = true;
        String msg = "";
        if (item.getDelFlag())
            return new R("事件已删除", null, Code.FAIL);
        if (item.getTitle() != null && item.getTitle().trim().isEmpty())
            return new R("标题不能为空", null, Code.FAIL);
        if (item.getTitle() != null && item.getTitle().trim().length() >= 50)
            return new R("标题长度不能大于50", null, Code.FAIL);
        if (item.getComplexity() != null) {
            if (item.getComplexity() > 2 || item.getComplexity() < 0) {
                b = false;
                msg = "复杂度不在范围";
            }
        } else b = false;
        return !b ? new R(msg, false, Code.FAIL) : new R("修改成功", true, Code.SUCCESS);
    }

    @Override
    public List<Item> getNowItem(HashMap<String, Object> element) {
        LambdaQueryWrapper<Item> element1 = new LambdaQueryWrapper<>();
        element1.eq(Item::getDelFlag, element.get("deleteFlag"));
        List<Item> items = itemMapper.selectList(element1);
        for (Item item1 : items) {

//            item1.setSubItems(itemMapper.getSubElementList(item1.getId()));
            item1.setUsers(userMapper.getUserByItem(item1));
        }
        return items;
    }

    private List<Item> getSubItemList(Item item) {
        LambdaQueryWrapper<Item> element1 = new LambdaQueryWrapper<>();
        element1.eq(Item::getDelFlag, item.getDelFlag());
        element1.eq(Item::getParentId, item.getId());
        List<Item> items = itemMapper.selectList(element1);
        for (Item item1 : items) {
            item1.setUsers(userMapper.getUserByItem(item1));
            item1.setSubItems(getSubItemList(item1));
        }
        return items;
    }
}
