package com.example.etudiant_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = EtudiantApiApplication.class)  // ← AJOUTEZ CECI
@TestPropertySource(properties = {
		"spring.classformat.ignore=true",
		"eureka.client.enabled=false",
		"spring.cloud.discovery.enabled=false",
		"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"
})
class EtudiantApiApplicationTests {

	@Test
	void contextLoads() {
		// Test de démarrage du contexte Spring
	}
}