package com.dong.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Áp dụng cho tất cả các endpoints
                .allowedOrigins("http://localhost:3000")  // Cho phép từ localhost:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Các phương thức HTTP được phép
                .allowCredentials(true);
    }
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .filter((request, next) -> next.exchange(request)
                        .timeout(Duration.ofSeconds(10))
                        .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))))
                .build();
    }
}

