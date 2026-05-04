package com.example.etudiant_api.integration;

import com.example.etudiant_api.entity.Departement;
import com.example.etudiant_api.entity.Etudiant;
import com.example.etudiant_api.repository.DepartementRepository;
import com.example.etudiant_api.repository.EtudiantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class EtudiantIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.cache.type", () -> "simple");
        registry.add("eureka.client.enabled", () -> "false");
        registry.add("spring.cloud.discovery.enabled", () -> "false");
    }

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        etudiantRepository.deleteAll();
        departementRepository.deleteAll();
    }

    @Test
    void shouldPersistAndRetrieveEtudiant() {
        Etudiant e = new Etudiant();
        e.setCin("C999");
        e.setNom("Test Etudiant");
        e.setDateNaissance(LocalDate.of(2000, 1, 1));
        e.setAnneePremiereInscription(2020);

        etudiantRepository.save(e);

        List<Etudiant> all = etudiantRepository.findAll();
        assertThat(all).isNotEmpty();
        assertThat(all.get(0).getNom()).isEqualTo("Test Etudiant");
    }

    @Test
    void shouldReturnEtudiantsViaAPI() {
        Etudiant e = new Etudiant();
        e.setCin("C888");
        e.setNom("API Test");
        e.setDateNaissance(LocalDate.of(2001, 5, 15));
        e.setAnneePremiereInscription(2021);
        etudiantRepository.save(e);

        ResponseEntity<Object[]> response = restTemplate
                .getForEntity("/api/etudiants", Object[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void shouldPersistDepartement() {
        Departement d = new Departement();
        d.setNom("Informatique");

        departementRepository.save(d);

        assertThat(departementRepository.findAll()).hasSize(1);
    }

    @Test
    void shouldFilterByAnnee() {
        Etudiant e1 = new Etudiant();
        e1.setCin("C001");
        e1.setNom("Etudiant 2021");
        e1.setDateNaissance(LocalDate.of(2002, 1, 1));
        e1.setAnneePremiereInscription(2021);
        etudiantRepository.save(e1);

        Etudiant e2 = new Etudiant();
        e2.setCin("C002");
        e2.setNom("Etudiant 2022");
        e2.setDateNaissance(LocalDate.of(2003, 1, 1));
        e2.setAnneePremiereInscription(2022);
        etudiantRepository.save(e2);

        List<Etudiant> result = etudiantRepository
                .findByAnneePremiereInscription(2021);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNom()).isEqualTo("Etudiant 2021");
    }
}