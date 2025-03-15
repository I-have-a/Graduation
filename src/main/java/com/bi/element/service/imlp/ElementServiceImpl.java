package com.bi.element.service.imlp;

import com.bi.element.common.BaseContext;
import com.bi.element.mapper.ElementMapper;
import com.bi.element.mapper.UserMapper;
import com.bi.element.pojo.Item;
import com.bi.element.response.Code;
import com.bi.element.response.R;
import com.bi.element.service.ElementService;
import com.bi.element.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ElementServiceImpl implements ElementService {

    @Autowired
    RedisCache redisCache;

    @Autowired
    ElementMapper elementMapper;


    @Autowired
    UserMapper userMapper;

    @Override
    public R deleteElement(Long id) {
        boolean flag = elementMapper.updateDelFlag(id);
        return flag ? new R("删除成功", true, Code.SUCCESS) : new R("删除失败", false, Code.FAIL);
    }

    @Transactional
    @Override
    public R addElement(Item item) {
        item.setFoundTime(new Date());
        boolean b = true;
        String msg = "";
        if (item.getTitle() != null) {
            if (item.getTitle().trim().length() <= 0) {
                b = false;
                msg = "标题不能为空";
            }
        } else b = false;
        if (item.getTitle() != null) {
            if (item.getTitle().trim().length() >= 50) {
                b = false;
                msg = "标题长度不能大于50";
            }
        } else b = false;
        if (item.getComplexity() != null) {
            if (item.getComplexity() > 2 || item.getComplexity() < 0) {
                b = false;
                msg = "复杂度不在范围";
            }
        } else b = false;
        if (item.getPStartTime() != null && item.getPEndTime() != null) {
            if (item.getPStartTime().compareTo(item.getPEndTime()) > 0) {
                b = false;
                msg = "计划开始时间不能大于结束时间";
            }
        }
        if (!b) return new R(msg, false, Code.FAIL);
        Long userid = BaseContext.getCurrentId();
        Integer elementID = elementMapper.addElement(item, userid);
        if (elementID > 0) {
            if (elementMapper.addUE(userid, elementID) > 0)
                return new R("新建完成", true, Code.SUCCESS);
        }
        return new R("新建失败", false, Code.FAIL);
    }

    @Override
    public R changeElement(Item item) {
        boolean b = true;
        String msg = "";
        if (item.isDeleteFlag())
            return new R("事件已删除", null, Code.FAIL);
        if (item.getTitle() != null && item.getTitle().trim().length() <= 0)
            return new R("标题不能为空", null, Code.FAIL);
        if (item.getTitle() != null && item.getTitle().trim().length() >= 50)
            return new R("标题长度不能大于50", null, Code.FAIL);
        if (item.getComplexity() != null) {
            if (item.getComplexity() > 2 || item.getComplexity() < 0) {
                b = false;
                msg = "复杂度不在范围";
            }
        } else b = false;
        if (item.getAccomplishStatus() != null) {
            if (item.getAccomplishStatus() > 2 && item.getAccomplishStatus() < 0) {
                b = false;
                msg = "状态错误";
            }
        } else b = false;
        if (item.getPStartTime() != null && item.getPEndTime() != null) {
            if (item.getPStartTime().compareTo(item.getPEndTime()) > 0) {
                b = false;
                msg = "计划开始时间不能大于结束时间";
            }
        }
        if (item.getStartTime() != null && item.getEndTime() != null) {
            if (item.getStartTime().compareTo(item.getEndTime()) > 0) {
                b = false;
                msg = "开始时间不能大于结束时间";
            }
        }
        if (!b) return new R(msg, false, Code.FAIL);
        if (elementMapper.updateElement(item) != 1) b = false;
        return !b ? new R(msg, false, Code.FAIL) : new R("修改成功", true, Code.SUCCESS);
    }

    @Override
    public List<Item> getNowElement(HashMap<String, Object> element) {
        List<Item> items = elementMapper.getElements(element);
        for (Item item1 : items) {
            item1.setSubItems(elementMapper.getSubElementList(item1.getId()));
            item1.setUsers(userMapper.getUserByElement(item1));
        }
        return items;
    }
}
