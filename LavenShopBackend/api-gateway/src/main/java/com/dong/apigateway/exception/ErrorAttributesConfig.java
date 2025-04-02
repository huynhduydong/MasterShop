package com.dong.apigateway.exception;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerRequest;

@Configuration
public class ErrorAttributesConfig {

    @Bean("customErrorAttributes")
    public DefaultErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
                Map<String, Object> errorAttributesMap = super.getErrorAttributes(request, options);
                errorAttributesMap.put("status", errorAttributesMap.get("status"));
                errorAttributesMap.put("message", errorAttributesMap.get("message"));
                
                // Add custom fields
                errorAttributesMap.put("apiGateway", "MasterShop API Gateway");
                errorAttributesMap.put("timestamp", errorAttributesMap.get("timestamp"));
                
                return errorAttributesMap;
            }
        };
    }
} 