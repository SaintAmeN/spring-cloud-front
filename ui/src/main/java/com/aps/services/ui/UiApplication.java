package com.aps.services.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication()
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.aps.services.ui", "com.aps.services.config", "com.aps.services.service", "com.aps.services.component", "com.aps.services.interceptors.feign"})
@EnableFeignClients(basePackages = {"com.aps.services.apiclients", "com.aps.services.ui.apiclients"})
public class UiApplication {

    public static void main(String[] args) {
        log.warn("Var: " + System.getProperty("machine"));
        SpringApplication.run(UiApplication.class, args);
    }
}
