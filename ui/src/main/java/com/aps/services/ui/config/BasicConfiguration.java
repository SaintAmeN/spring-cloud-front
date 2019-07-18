package com.aps.services.ui.config;

import com.aps.services.ui.component.SessionScopeComponent;
import com.fasterxml.jackson.databind.Module;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableSpringDataWebSupport
public class BasicConfiguration implements WebMvcConfigurer {

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Module pageJacksonModule() {
        return new PageJacksonModule();
    }
}
