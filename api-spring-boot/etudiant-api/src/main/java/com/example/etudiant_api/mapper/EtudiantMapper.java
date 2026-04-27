package com.example.etudiant_api.mapper;

import com.example.etudiant_api.dto.EtudiantDTO;
import com.example.etudiant_api.entity.Etudiant;
import com.example.etudiant_api.entity.Departement;
import org.springframework.stereotype.Component;

@Component
public class EtudiantMapper {

    public EtudiantDTO toDto(Etudiant etudiant) {
        EtudiantDTO dto = new EtudiantDTO();
        dto.setId(etudiant.getId());
        dto.setCin(etudiant.getCin());
        dto.setNom(etudiant.getNom());
        dto.setDateNaissance(etudiant.getDateNaissance());
        dto.setEmail(etudiant.getEmail());
        dto.setAnneePremiereInscription(etudiant.getAnneePremiereInscription());
        if (etudiant.getDepartement() != null) {
            dto.setDepartementId(etudiant.getDepartement().getId());
            dto.setDepartementNom(etudiant.getDepartement().getNom());
        }
        return dto;
    }

    public Etudiant toEntity(EtudiantDTO dto) {
        Etudiant etudiant = new Etudiant();
        etudiant.setId(dto.getId());
        etudiant.setCin(dto.getCin());
        etudiant.setNom(dto.getNom());
        etudiant.setDateNaissance(dto.getDateNaissance());
        etudiant.setEmail(dto.getEmail());
        etudiant.setAnneePremiereInscription(dto.getAnneePremiereInscription());
        return etudiant;
    }
}