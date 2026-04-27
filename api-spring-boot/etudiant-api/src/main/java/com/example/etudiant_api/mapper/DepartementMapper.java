package com.example.etudiant_api.mapper;

import com.example.etudiant_api.dto.DepartementDTO;
import com.example.etudiant_api.entity.Departement;
import org.springframework.stereotype.Component;

@Component
public class DepartementMapper {

    public DepartementDTO toDto(Departement d) {
        DepartementDTO dto = new DepartementDTO();
        dto.setId(d.getId());
        dto.setNom(d.getNom());
        return dto;
    }

    public Departement toEntity(DepartementDTO dto) {
        Departement d = new Departement();
        d.setId(dto.getId());
        d.setNom(dto.getNom());
        return d;
    }
}