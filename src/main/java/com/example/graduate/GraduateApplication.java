package com.example.graduate;

import com.example.graduate.service.InitializedDataService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = "com.example.graduate")
@ComponentScan(basePackages = "com.example")
@EnableTransactionManagement//开启事务管理功能
@EnableWebMvc
@MapperScan("com.example.graduate.mapper")
public class GraduateApplication implements ApplicationRunner {

    @Autowired
    private InitializedDataService initializedDataService;

    public static void main(String[] args) {
        SpringApplication.run(GraduateApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        initializedDataService.initProductInfo();
    }
}
