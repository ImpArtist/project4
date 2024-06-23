package com.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.project.mapper")
@SpringBootApplication
public class DimensionAnalysisApplication {
    public static void main(String[] args) {
        SpringApplication.run(DimensionAnalysisApplication.class, args);
    }
}