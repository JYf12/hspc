package com.example.huashengpc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.huashengpc.mapper")
public class HuashengpcApplication {

    public static void main(String[] args) {
        SpringApplication.run(HuashengpcApplication.class, args);
    }

}
