package com.example.graduate.common;

public class RedisConstant {
    public static final String CACHE_CLASS_PREFIX = "class:";
    public static final String LOGIN_PREFIX = "login:";
    public static final String SURVIVAL_PREFIX = "survival:";
    public static final Long CACHE_NULL_TIME = 2L;

    public static String multi(String s) {
        return "ofc-pincode-" + s + "-*";
    }
}
