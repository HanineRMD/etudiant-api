package com.example.etudiant_api.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EtudiantDTO {
    private Long id;
    private String cin;
    private String nom;
    private LocalDate dateNaissance;
    private String email;
    private int anneePremiereInscription;
    private Long departementId;
    private String departementNom;
}