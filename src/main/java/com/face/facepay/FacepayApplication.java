package com.face.facepay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.face.facepay.dao")
public class FacepayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FacepayApplication.class, args);
    }

}

