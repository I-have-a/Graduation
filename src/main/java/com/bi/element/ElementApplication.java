package com.bi.element;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@MapperScan("com.bi.element.mapper")
@ComponentScan(basePackages = "com.bi")
@EnableTransactionManagement//开启事务管理功能
@EnableWebMvc
public class ElementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElementApplication.class, args);
    }
}
