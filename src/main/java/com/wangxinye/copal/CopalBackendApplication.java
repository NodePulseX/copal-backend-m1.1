package com.wangxinye.copal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wangxinye.copal.mapper") // 匹配你的mapper包路径
public class CopalBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(CopalBackendApplication.class, args);
        System.out.println("======================================");
        System.out.println("Copal Backend Started Successfully!");
        System.out.println("登录接口：http://localhost:8080/copal/auth/login");
        System.out.println("======================================");
    }
}