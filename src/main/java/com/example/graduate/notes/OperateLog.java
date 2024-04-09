package com.example.graduate.notes;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLog {

    // 日志内容如：用户:{0},修改了名称为:{1},年龄:{2}
    String content() default "-";

    // 参数名通过逗号分割如：id, name, age
    String params() default "";
}


