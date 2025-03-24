package com.bi.element.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnalyzeParamsUtils {

    // 解析参数，赋予实际的数据值
    public static Object[] analyzeParams(JoinPoint point, String params) {
        if (params == null || params.isEmpty()) return new Object[0];
        List<Object> list = new ArrayList<>();
        Arrays.stream(params.split(",")).filter(s -> !s.isEmpty()).forEach(s -> {
            try {
                list.add(getParamValue(point, s));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return list.toArray();
    }

    // 从参数列表中根据参数链获取值
    private static Object getParamValue(JoinPoint point, String params) throws Exception {
        String[] split = params.split("\\.");
        int length = split.length;

        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        String[] argNames = methodSignature.getParameterNames();
        Object[] argValues = point.getArgs();
        int argLength = argNames.length;

        for (int i = 0; i < argLength; i++) {
            Object arg = argValues[i];
            final Class<?> clazz = arg.getClass();
            final String name = clazz.getSimpleName();
            if (name.equals(split[0])) { // 判断是否是指定类
                if (length == 1) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    return objectMapper.writeValueAsString(arg);
                } else {
                    return getParamValue(arg, split[1]);
                }
            } else if (argNames[i].equals(split[0])) {
                return arg;
            }
        }
        return new Object();
    }

    // 获取指定对象的指定字段的值
    private static Object getParamValue(Object argValue, String param) throws Exception {
        String getMethodName = "get" + firstUpperCase(param);
        Method declaredMethod = argValue.getClass().getMethod(getMethodName);
        return declaredMethod.invoke(argValue);
    }

    // 首字母大写转换
    private static String firstUpperCase(String field) {
        if (!StringUtils.isEmpty(field)) {
            char[] cs = field.toCharArray();
            cs[0] -= 32;
            return String.valueOf(cs);
        } else {
            return field;
        }
    }
}

