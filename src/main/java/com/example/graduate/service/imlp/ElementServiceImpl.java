package com.example.graduate.service.imlp;

import com.alibaba.fastjson.JSONObject;
import com.example.graduate.common.BaseContext;
import com.example.graduate.common.RedisConstant;
import com.example.graduate.mapper.ElementMapper;
import com.example.graduate.pojo.Element;
import com.example.graduate.pojo.User;
import com.example.graduate.pojo.UserDetailsImlp;
import com.example.graduate.response.Code;
import com.example.graduate.response.R;
import com.example.graduate.service.ElementService;
import com.example.graduate.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ElementServiceImpl implements ElementService {

    @Autowired
    RedisCache redisCache;

    @Autowired
    ElementMapper elementMapper;

    @Override
    public R getList() {
        Long userID = BaseContext.getCurrentId();
        JSONObject cacheObject = redisCache.getCacheObject(RedisConstant.LOGIN_PREFIX + userID);
        UserDetailsImlp userDetails = cacheObject.toJavaObject(UserDetailsImlp.class);
        User user = userDetails.getUser();
        JSONObject o;
        if (user.getElements() == null) {
            List<Element> elementListByUserID = elementMapper.getElementListByUserID(userID);
            if (elementListByUserID != null) {
                user.setElements(elementListByUserID);
                userDetails.setUser(user);
                o = redisCache.updateObject(RedisConstant.LOGIN_PREFIX + userID, userDetails);
            } else return new R("事件列表为空", null, Code.SUCCESS);
        } else {
            return new R("", user, Code.SUCCESS);
        }
        if (o == null) return new R("获取失败", false, Code.FAIL);
        return new R("获取成功", user, Code.SUCCESS);
    }

    @Override
    public R deleteElement(Long id) {
        boolean flag = elementMapper.updateDelFlag(id);
        User user = null;
        if (flag) {
            Long userID = BaseContext.getCurrentId();
            JSONObject cacheObject = redisCache.getCacheObject(RedisConstant.LOGIN_PREFIX + userID);
            UserDetailsImlp userDetails = cacheObject.toJavaObject(UserDetailsImlp.class);
            user = userDetails.getUser();
            user.setElements(elementMapper.getElementListByUserID(userID));
            userDetails.setUser(user);
            JSONObject o = redisCache.updateObject(RedisConstant.LOGIN_PREFIX + userID, userDetails);
            if (o == null) {
                return new R("哦豁", false, Code.FAIL);
            }
        }
        return new R("删除成功", user, Code.SUCCESS);
    }

    @Override
    public R addElement(Element element) {
        element.setFoundTime(new Date());
        if (elementMapper.addElement(element, BaseContext.getCurrentId()) == 1)
            return new R("新建完成", true, Code.SUCCESS);
        return new R("新建失败", false, Code.FAIL);
    }

    @Override
    public R changeElement(Element element) {
        boolean b = true;
        String msg = "";
        if (element.isDeleteFlag()) {
            return new R("事件已删除", null, Code.FAIL);
        }
        if (element.getTitle().trim().length() <= 0) {
            return new R("标题不能为空", null, Code.FAIL);
        }
        if (element.getTitle().trim().length() >= 50) {
            return new R("标题长度不能大于50", null, Code.FAIL);
        }
        if (element.getComplexity() != null) {
            if (element.getComplexity() > 2 && element.getComplexity() < 0) {
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
}
