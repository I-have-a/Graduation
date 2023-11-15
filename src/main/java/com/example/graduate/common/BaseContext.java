package com.example.graduate.common;

/**
 * 基于ThreadLocal封装类，用于保存和获取当前用户ID
 */
public class BaseContext {
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }
}
