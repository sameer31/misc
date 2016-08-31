package com.yakshop.app;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Sunny Mourya
 *
 * This is the starting point of YakShop application	
 */
@ComponentScan
@EnableAutoConfiguration
public class Application {	
	/**
     * Starts the Spring Boot Application
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }   
}