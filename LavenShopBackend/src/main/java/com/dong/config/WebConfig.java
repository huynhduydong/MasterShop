package com.dong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Cho phép CORS cho tất cả các URL
                .allowedOrigins("http://localhost:3000")  // Cho phép từ localhost:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Các phương thức HTTP được phép
                .allowedHeaders("*")  // Cho phép tất cả các header
                .allowCredentials(true);  // Cho phép gửi thông tin đăng nhập (cookies, authorization headers)
    }
}

