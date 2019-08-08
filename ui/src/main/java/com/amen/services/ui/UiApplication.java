package com.amen.services.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = "com.aps.services.ui.model.event")
@ComponentScan(basePackages = {
        "com.aps.services.ui",
        "com.aps.services.ui.config",
        "com.aps.services.ui.service",
        "com.aps.services.ui.component",
        "feign",
        "com.aps.services.ui.model.event"
})
@EnableFeignClients(basePackages = {"com.aps.services.ui.apiclients", "com.aps.services.ui.apiclients"})
public class UiApplication {

    public static void main(String[] args) {
        log.warn("Var: " + System.getProperty("machine"));
        SpringApplication.run(UiApplication.class, args);
    }
}
