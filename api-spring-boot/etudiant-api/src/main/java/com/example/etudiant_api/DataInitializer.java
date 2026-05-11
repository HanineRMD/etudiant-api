package com.example.etudiant_api;

import com.example.etudiant_api.entity.Etudiant;
import com.example.etudiant_api.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Override
    public void run(String... args) throws Exception {
        if (etudiantRepository.count() == 0) {
            // Création des étudiants en utilisant les setters
            Etudiant e1 = new Etudiant();
            e1.setEmail("fatima.zahra@example.com");
            e1.setAnneePremiereInscription(2022);
            e1.setCin("C123456");
            e1.setNom("Fatima Zahra");
            e1.setDateNaissance(LocalDate.of(2000, 5, 15));

            Etudiant e2 = new Etudiant();
            e2.setEmail("mohammed.ali@example.com");
            e2.setAnneePremiereInscription(2021);
            e2.setCin("C789012");
            e2.setNom("Mohammed Ali");
            e2.setDateNaissance(LocalDate.of(1999, 8, 22));

            Etudiant e3 = new Etudiant();
            e3.setEmail("aisha.benali@example.com");
            e3.setAnneePremiereInscription(2023);
            e3.setCin("C345678");
            e3.setNom("Aisha Benali");
            e3.setDateNaissance(LocalDate.of(2001, 3, 10));

            Etudiant e4 = new Etudiant();
            e4.setEmail("youssef.elamri@example.com");
            e4.setAnneePremiereInscription(2022);
            e4.setCin("C901234");
            e4.setNom("Youssef El Amri");
            e4.setDateNaissance(LocalDate.of(2000, 11, 30));

            Etudiant e5 = new Etudiant();
            e5.setEmail("khadija.mansouri@example.com");
            e5.setAnneePremiereInscription(2023);
            e5.setCin("C567890");
            e5.setNom("Khadija Mansouri");
            e5.setDateNaissance(LocalDate.of(2002, 7, 18));

            etudiantRepository.save(e1);
            etudiantRepository.save(e2);
            etudiantRepository.save(e3);
            etudiantRepository.save(e4);
            etudiantRepository.save(e5);

            System.out.println("5 étudiants ont été ajoutés à la base de données");
        }
    }
}