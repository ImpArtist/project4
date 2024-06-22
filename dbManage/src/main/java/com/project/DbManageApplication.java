package com.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.project.mapper")
@SpringBootApplication
public class DbManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(DbManageApplication.class, args);
    }
}