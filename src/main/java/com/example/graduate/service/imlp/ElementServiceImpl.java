package com.example.graduate.service.imlp;

import com.example.graduate.common.BaseContext;
import com.example.graduate.mapper.ElementMapper;
import com.example.graduate.mapper.SubElementMapper;
import com.example.graduate.mapper.UserMapper;
import com.example.graduate.pojo.Element;
import com.example.graduate.response.Code;
import com.example.graduate.response.R;
import com.example.graduate.service.ElementService;
import com.example.graduate.utils.RedisCache;
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
    SubElementMapper subElementMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public R deleteElement(Long id) {
        boolean flag = elementMapper.updateDelFlag(id);
        return flag ? new R("删除成功", true, Code.SUCCESS) : new R("删除失败", false, Code.FAIL);
    }

    @Transactional
    @Override
    public R addElement(Element element) {
        element.setFoundTime(new Date());
        boolean b = true;
        String msg = "";
        if (element.getTitle() != null) {
            if (element.getTitle().trim().length() <= 0) {
                b = false;
                msg = "标题不能为空";
            }
        } else b = false;
        if (element.getTitle() != null) {
            if (element.getTitle().trim().length() >= 50) {
                b = false;
                msg = "标题长度不能大于50";
            }
        } else b = false;
        if (element.getComplexity() != null) {
            if (element.getComplexity() > 2 || element.getComplexity() < 0) {
                b = false;
                msg = "复杂度不在范围";
            }
        } else b = false;
        if (element.getPStartTime() != null && element.getPEndTime() != null) {
            if (element.getPStartTime().compareTo(element.getPEndTime()) > 0) {
                b = false;
                msg = "计划开始时间不能大于结束时间";
            }
        }
        if (!b) return new R(msg, false, Code.FAIL);
        Long userid = BaseContext.getCurrentId();
        Integer elementID = elementMapper.addElement(element, userid);
        if (elementID > 0)
            if (elementMapper.addUE(userid, elementID) > 0)
                return new R("新建完成", true, Code.SUCCESS);
        return new R("新建失败", false, Code.FAIL);
    }

    @Override
    public R changeElement(Element element) {
        boolean b = true;
        String msg = "";
        if (element.isDeleteFlag())
            return new R("事件已删除", null, Code.FAIL);
        if (element.getTitle() != null && element.getTitle().trim().length() <= 0)
            return new R("标题不能为空", null, Code.FAIL);
        if (element.getTitle() != null && element.getTitle().trim().length() >= 50)
            return new R("标题长度不能大于50", null, Code.FAIL);
        if (element.getComplexity() != null) {
            if (element.getComplexity() > 2 || element.getComplexity() < 0) {
                b = false;
                msg = "复杂度不在范围";
            }
        } else b = false;
        if (element.getAccomplishStatus() != null) {
            if (element.getAccomplishStatus() > 2 && element.getAccomplishStatus() < 0) {
                b = false;
                msg = "状态错误";
            }
        } else b = false;
        if (element.getPStartTime() != null && element.getPEndTime() != null) {
            if (element.getPStartTime().compareTo(element.getPEndTime()) > 0) {
                b = false;
                msg = "计划开始时间不能大于结束时间";
            }
        }
        if (element.getStartTime() != null && element.getEndTime() != null) {
            if (element.getStartTime().compareTo(element.getEndTime()) > 0) {
                b = false;
                msg = "开始时间不能大于结束时间";
            }
        }
        if (!b) return new R(msg, false, Code.FAIL);
        if (elementMapper.updateElement(element) != 1) b = false;
        return !b ? new R(msg, false, Code.FAIL) : new R("修改成功", true, Code.SUCCESS);
    }

    @Override
    public List<Element> getNowElement(HashMap<String, Object> element) {
        List<Element> elements = elementMapper.getElements(element);
        for (Element element1 : elements) {
            element1.setSubElements(subElementMapper.getSubElementList(element1.getId()));
            element1.setUsers(userMapper.getUserByElement(element1));
        }
        return elements;
    }
}
