package com.lcf.controller;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)

@MapperScan(basePackages = "com.lcf.mapper")
@ComponentScan(basePackages = {"com.lcf","org.n3r.idworker"})
public class webApplacation {

    public static void main(String[] args) {
        SpringApplication.run(webApplacation.class, args);
    }
}