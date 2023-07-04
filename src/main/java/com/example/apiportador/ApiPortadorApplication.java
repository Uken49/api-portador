package com.example.apiportador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ApiPortadorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiPortadorApplication.class, args);
    }

}
