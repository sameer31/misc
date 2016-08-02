package com.microservices.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import com.microservices.pricing.PriceConfiguration;


@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(PriceConfiguration.class)
public class PricingServer {
    /**
     * Starts the Pricing Server Spring Boot Application
     * 
     * @param args
     * @throws Exception
     */
  public static void main(String[] args) {
    System.setProperty("spring.config.name", "pricing_server");
    SpringApplication.run(PricingServer.class, args);
  }
}