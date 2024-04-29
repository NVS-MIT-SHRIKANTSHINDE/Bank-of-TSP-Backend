package com.TransactionService1.TransactionService1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@SpringBootApplication
@EntityScan("com.TransactionService1")
@EnableJpaRepositories("com.TransactionService1")

public class TransactionService1Application {

	 public static void main(String[] args) {
	        SpringApplication.run(TransactionService1Application.class, args);
	        System.out.println("Transaction Service");
	    }

	    @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	  
	        	@Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**")
	                        .allowedOrigins("http://localhost:3000") // Replace with your Angular app URL
	                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
	                        .allowCredentials(true);
	                System.out.println("Transaction Service");
	            }
	        };
	    }

}
