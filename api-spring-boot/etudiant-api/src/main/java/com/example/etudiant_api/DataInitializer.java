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
            // Création des étudiants avec le bon constructeur @AllArgsConstructor
            Etudiant e1 = new Etudiant(
                    null,                    // id (auto-généré)
                    "fatima.zahra@example.com",  // email
                    2022,                    // anneePremiereInscription
                    "C123456",               // cin
                    "Fatima Zahra",          // nom
                    LocalDate.of(2000, 5, 15),  // dateNaissance
                    null                     // departement (null pour l'instant)
            );

            Etudiant e2 = new Etudiant(
                    null,
                    "mohammed.ali@example.com",
                    2021,
                    "C789012",
                    "Mohammed Ali",
                    LocalDate.of(1999, 8, 22),
                    null
            );

            Etudiant e3 = new Etudiant(
                    null,
                    "aisha.benali@example.com",
                    2023,
                    "C345678",
                    "Aisha Benali",
                    LocalDate.of(2001, 3, 10),
                    null
            );

            Etudiant e4 = new Etudiant(
                    null,
                    "youssef.elamri@example.com",
                    2022,
                    "C901234",
                    "Youssef El Amri",
                    LocalDate.of(2000, 11, 30),
                    null
            );

            Etudiant e5 = new Etudiant(
                    null,
                    "khadija.mansouri@example.com",
                    2023,
                    "C567890",
                    "Khadija Mansouri",
                    LocalDate.of(2002, 7, 18),
                    null
            );

            etudiantRepository.save(e1);
            etudiantRepository.save(e2);
            etudiantRepository.save(e3);
            etudiantRepository.save(e4);
            etudiantRepository.save(e5);

            System.out.println("5 étudiants ont été ajoutés à la base de données");
        }
    }
}