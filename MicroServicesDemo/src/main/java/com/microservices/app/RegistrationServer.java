package com.microservices.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class RegistrationServer {
    /**
     * Starts the Registration Server Spring Boot Application
     * 
     * @param args
     * @throws Exception
     */
  public static void main(String[] args) {
    System.setProperty("spring.config.name", "registration_server");
    SpringApplication.run(RegistrationServer.class, args);
  }
}