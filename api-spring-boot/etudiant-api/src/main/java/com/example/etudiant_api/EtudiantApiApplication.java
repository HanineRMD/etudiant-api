package com.example.etudiant_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@SpringBootApplication
@EnableCaching
public class EtudiantApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtudiantApiApplication.class, args);
	}

}
