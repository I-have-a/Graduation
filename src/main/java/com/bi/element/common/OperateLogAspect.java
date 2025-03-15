package com.bi.element.common;

import com.bi.element.notes.OperateLog;
import com.bi.element.utils.AnalyzeParamsUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.MessageFormat;

@Aspect
@Component
public class OperateLogAspect {

    @Pointcut("@annotation(com.bi.element.notes.OperateLog)")
    public void pointcut() {
    }

    @AfterReturning("pointcut()")
    public void afterReturning(JoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        OperateLog annotation = method.getAnnotation(OperateLog.class);
        final String content = annotation.content();
        final String params = annotation.params();

        Object[] strArr = AnalyzeParamsUtils.analyzeParams(point, params);
        String formatContent = "【操作成功】" + MessageFormat.format(content, strArr);
        System.out.println(formatContent);
        // TODO 保存数据库mysql或文件 ...
    }
}

