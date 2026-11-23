package com.hkcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HkcareApplication {
    public static void main(String[] args) {
        SpringApplication.run(HkcareApplication.class, args);
        System.out.println("HKCARE chạy tại http://localhost:3000/index.html");
    }
}
