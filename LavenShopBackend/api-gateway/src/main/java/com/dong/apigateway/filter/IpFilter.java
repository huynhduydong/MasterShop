package com.dong.apigateway.filter;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class IpFilter implements GlobalFilter, Ordered {

    @Value("${security.ip.blacklist:}")
    private String blacklistIps;

    @Value("${security.ip.whitelist:}")
    private String whitelistIps;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        if (remoteAddress == null) {
            log.warn("Remote address is null");
            return chain.filter(exchange);
        }

        String clientIp = remoteAddress.getHostString();
        log.debug("Incoming request from IP: {}", clientIp);

        // Check whitelist first (if configured)
        List<String> whitelist = parseIpList(whitelistIps);
        if (!whitelist.isEmpty() && !isIpInList(clientIp, whitelist)) {
            log.warn("IP {} is not in whitelist", clientIp);
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        // Then check blacklist
        List<String> blacklist = parseIpList(blacklistIps);
        if (!blacklist.isEmpty() && isIpInList(clientIp, blacklist)) {
            log.warn("IP {} is in blacklist", clientIp);
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // Execute this filter before other filters
        return -100;
    }

    private List<String> parseIpList(String ipList) {
        if (ipList == null || ipList.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.asList(ipList.split(","));
    }

    private boolean isIpInList(String ip, List<String> ipList) {
        return ipList.contains(ip);
    }
} 