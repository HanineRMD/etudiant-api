package com.example.etudiant_api.mapper;

import com.example.etudiant_api.dto.EtudiantDTO;
import com.example.etudiant_api.entity.Departement;
import com.example.etudiant_api.entity.Etudiant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class EtudiantMapperTest {

    private EtudiantMapper etudiantMapper;
    private Etudiant etudiant;
    private EtudiantDTO etudiantDTO;
    private Departement departement;

    @BeforeEach
    void setUp() {
        etudiantMapper = new EtudiantMapper();

        departement = new Departement();
        departement.setId(1L);
        departement.setNom("Informatique");

        etudiant = new Etudiant();
        etudiant.setId(1L);
        etudiant.setCin("C123456");
        etudiant.setNom("Ahmed Ben Ali");
        etudiant.setDateNaissance(LocalDate.of(2000, 5, 15));
        etudiant.setEmail("ahmed@test.com");
        etudiant.setAnneePremiereInscription(2020);
        etudiant.setDepartement(departement);

        etudiantDTO = new EtudiantDTO();
        etudiantDTO.setId(1L);
        etudiantDTO.setCin("C123456");
        etudiantDTO.setNom("Ahmed Ben Ali");
        etudiantDTO.setDateNaissance(LocalDate.of(2000, 5, 15));
        etudiantDTO.setEmail("ahmed@test.com");
        etudiantDTO.setAnneePremiereInscription(2020);
        etudiantDTO.setDepartementId(1L);
        etudiantDTO.setDepartementNom("Informatique");
    }

    @Test
    void toDto_ShouldMapEntityToDTO() {
        EtudiantDTO result = etudiantMapper.toDto(etudiant);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(etudiant.getId());
        assertThat(result.getCin()).isEqualTo(etudiant.getCin());
        assertThat(result.getNom()).isEqualTo(etudiant.getNom());
        assertThat(result.getDateNaissance()).isEqualTo(etudiant.getDateNaissance());
        assertThat(result.getEmail()).isEqualTo(etudiant.getEmail());
        assertThat(result.getAnneePremiereInscription()).isEqualTo(etudiant.getAnneePremiereInscription());
        assertThat(result.getDepartementId()).isEqualTo(etudiant.getDepartement().getId());
        assertThat(result.getDepartementNom()).isEqualTo(etudiant.getDepartement().getNom());
    }

    @Test
    void toDto_WithNullDepartement_ShouldMapWithoutDepartement() {
        etudiant.setDepartement(null);

        EtudiantDTO result = etudiantMapper.toDto(etudiant);

        assertThat(result).isNotNull();
        assertThat(result.getDepartementId()).isNull();
        assertThat(result.getDepartementNom()).isNull();
    }

    @Test
    void toDto_WithNullEntity_ShouldReturnNull() {
        EtudiantDTO result = etudiantMapper.toDto(null);
        assertThat(result).isNull();
    }

    @Test
    void toEntity_ShouldMapDTOToEntity() {
        Etudiant result = etudiantMapper.toEntity(etudiantDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(etudiantDTO.getId());
        assertThat(result.getCin()).isEqualTo(etudiantDTO.getCin());
        assertThat(result.getNom()).isEqualTo(etudiantDTO.getNom());
        assertThat(result.getDateNaissance()).isEqualTo(etudiantDTO.getDateNaissance());
        assertThat(result.getEmail()).isEqualTo(etudiantDTO.getEmail());
        assertThat(result.getAnneePremiereInscription()).isEqualTo(etudiantDTO.getAnneePremiereInscription());
        assertThat(result.getDepartement()).isNull();
    }

    @Test
    void toEntity_WithNullDTO_ShouldReturnNull() {
        Etudiant result = etudiantMapper.toEntity(null);
        assertThat(result).isNull();
    }
}