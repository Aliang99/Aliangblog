package com.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BloApplication {
    public static void main(String[] args) {
        SpringApplication.run(BloApplication.class, args);
        System.out.println("首页访问地址："+"http://localhost:80/index");
    }

}
