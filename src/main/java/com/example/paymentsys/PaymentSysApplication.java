package com.example.paymentsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PaymentSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentSysApplication.class, args);
    }

}
