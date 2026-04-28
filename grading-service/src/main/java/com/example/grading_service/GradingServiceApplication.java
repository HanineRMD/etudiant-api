package com.example.grading_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients  // ✅ Important pour activer Feign
public class GradingServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(GradingServiceApplication.class, args);
	}
}