package com.imchatserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.imchatserver.mapper")
public class ImchatServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImchatServerApplication.class, args);
    }

}
