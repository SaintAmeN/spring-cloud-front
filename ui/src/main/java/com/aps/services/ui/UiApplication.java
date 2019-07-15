package com.aps.services.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.aps.services.ui", "com.aps.services.config", "com.aps.services.service"})
@EnableFeignClients(basePackages = {"com.aps.services.apiclients", "com.aps.services.ui.apiclients"})
public class UiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }
}
