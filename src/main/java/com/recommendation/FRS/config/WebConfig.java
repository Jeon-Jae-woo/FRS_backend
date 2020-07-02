package com.recommendation.FRS.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 공동 작업하는 경우 ip 체크 필요
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("http://172.31.19.223:3001")
                .allowedOrigins("http://172.25.231.51:3000") // 지은
                .allowedOrigins("http://172.31.16.31:3000") // 원종
                .allowedOrigins("http://172.31.16.30:3000"); // 연지

    }
}
