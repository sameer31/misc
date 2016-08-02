package com.microservices.app;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan
public class ApplicationServer {

	public static final String PRODUCT_SERVER_URL = "http://PRODUCT_SERVER";
	
	public static final String PRICE_SERVER_URL = "http://PRICE_SERVER";
	
	/**
     * Starts the Spring Boot Application
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args){
    	System.setProperty("spring.config.name", "application_server");
        SpringApplication.run(ApplicationServer.class, args);
    }
    
    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    
}