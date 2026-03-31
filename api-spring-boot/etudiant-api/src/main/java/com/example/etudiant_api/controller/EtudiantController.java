package com.example.etudiant_api.controller;

import com.example.etudiant_api.entity.Etudiant;
import com.example.etudiant_api.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
@CrossOrigin(origins = "*") // Pour permettre à l'app mobile d'accéder à l'API
public class EtudiantController {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @GetMapping
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }
}