package com.geostar.cloud.gateway.web;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.geostar.cloud.auth.client")
@EnableCircuitBreaker
@EnableMethodCache(basePackages = "com.geostar.cloud.gateway.web")
@EnableCreateCacheAnnotation
public class GatewayWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayWebApplication.class, args);
	}

}
