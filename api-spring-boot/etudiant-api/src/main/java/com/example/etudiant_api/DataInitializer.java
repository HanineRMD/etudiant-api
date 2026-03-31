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
            etudiantRepository.save(new Etudiant(null, "C123456", "Fatima Zahra", LocalDate.of(2000, 5, 15)));
            etudiantRepository.save(new Etudiant(null, "C789012", "Mohammed Ali", LocalDate.of(1999, 8, 22)));
            etudiantRepository.save(new Etudiant(null, "C345678", "Aisha Benali", LocalDate.of(2001, 3, 10)));
            etudiantRepository.save(new Etudiant(null, "C901234", "Youssef El Amri", LocalDate.of(2000, 11, 30)));
            etudiantRepository.save(new Etudiant(null, "C567890", "Khadija Mansouri", LocalDate.of(2002, 7, 18)));

            System.out.println("5 étudiants ont été ajoutés à la base de données");
        }
    }
}