package com.microservices.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import com.microservices.product.ProductConfiguration;


@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(ProductConfiguration.class)
public class ProductServer {
    /**
     * Starts the Product Server Spring Boot Application
     * 
     * @param args
     * @throws Exception
     */
  public static void main(String[] args) {
    System.setProperty("spring.config.name", "product_server");
    SpringApplication.run(ProductServer.class, args);
  }
}