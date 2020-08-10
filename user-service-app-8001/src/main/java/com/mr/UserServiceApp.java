package com.mr;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zhaomanzhou
 */


@SpringBootApplication
@EnableDubbo
@MapperScan("com.mr.mapper")
public class
UserServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp.class, args);
    }
}
