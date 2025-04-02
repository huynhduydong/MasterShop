package com.dong.apigateway.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/info")
@RequiredArgsConstructor
public class ApiInfoController {

    private final DiscoveryClient discoveryClient;
    
    @Value("${spring.application.name}")
    private String applicationName;
    
    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getApiInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", applicationName);
        info.put("profile", activeProfile);
        info.put("version", "1.0.0");
        info.put("timestamp", System.currentTimeMillis());
        
        // Add system info
        Map<String, String> systemInfo = new HashMap<>();
        systemInfo.put("javaVersion", System.getProperty("java.version"));
        systemInfo.put("osName", System.getProperty("os.name"));
        systemInfo.put("osVersion", System.getProperty("os.version"));
        systemInfo.put("availableProcessors", String.valueOf(Runtime.getRuntime().availableProcessors()));
        systemInfo.put("freeMemory", String.valueOf(Runtime.getRuntime().freeMemory() / (1024 * 1024)) + " MB");
        systemInfo.put("maxMemory", String.valueOf(Runtime.getRuntime().maxMemory() / (1024 * 1024)) + " MB");
        
        info.put("system", systemInfo);
        
        // Add services info
        Map<String, List<Map<String, String>>> services = new HashMap<>();
        discoveryClient.getServices().forEach(serviceName -> {
            List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
            List<Map<String, String>> instanceDetails = instances.stream()
                    .map(instance -> {
                        Map<String, String> details = new HashMap<>();
                        details.put("instanceId", instance.getInstanceId());
                        details.put("host", instance.getHost());
                        details.put("port", String.valueOf(instance.getPort()));
                        details.put("uri", instance.getUri().toString());
                        details.put("secure", String.valueOf(instance.isSecure()));
                        return details;
                    })
                    .collect(Collectors.toList());
            services.put(serviceName, instanceDetails);
        });
        
        info.put("services", services);
        
        return ResponseEntity.ok(info);
    }
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> getHealthInfo() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", System.currentTimeMillis());
        
        // Add memory info
        Map<String, String> memory = new HashMap<>();
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory() / (1024 * 1024);
        long freeMemory = runtime.freeMemory() / (1024 * 1024);
        long usedMemory = totalMemory - freeMemory;
        
        memory.put("total", totalMemory + " MB");
        memory.put("free", freeMemory + " MB");
        memory.put("used", usedMemory + " MB");
        memory.put("usagePercentage", String.format("%.2f%%", (usedMemory * 100.0) / totalMemory));
        
        health.put("memory", memory);
        
        // Add services health
        Map<String, String> servicesHealth = new HashMap<>();
        discoveryClient.getServices().forEach(serviceName -> {
            List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
            servicesHealth.put(serviceName, instances.isEmpty() ? "DOWN" : "UP");
        });
        
        health.put("services", servicesHealth);
        
        return ResponseEntity.ok(health);
    }
} 