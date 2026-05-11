package com.example.etudiant_api.mapper;

import com.example.etudiant_api.dto.DepartementDTO;
import com.example.etudiant_api.entity.Departement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DepartementMapperTest {

    private DepartementMapper departementMapper;
    private Departement departement;
    private DepartementDTO departementDTO;

    @BeforeEach
    void setUp() {
        departementMapper = new DepartementMapper();

        departement = new Departement();
        departement.setId(1L);
        departement.setNom("Informatique");

        departementDTO = new DepartementDTO();
        departementDTO.setId(1L);
        departementDTO.setNom("Informatique");
    }

    @Test
    void toDto_ShouldMapEntityToDTO() {
        DepartementDTO result = departementMapper.toDto(departement);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(departement.getId());
        assertThat(result.getNom()).isEqualTo(departement.getNom());
    }

    @Test
    void toDto_WithNullEntity_ShouldReturnNull() {
        DepartementDTO result = departementMapper.toDto(null);
        assertThat(result).isNull();
    }

    @Test
    void toEntity_ShouldMapDTOToEntity() {
        Departement result = departementMapper.toEntity(departementDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(departementDTO.getId());
        assertThat(result.getNom()).isEqualTo(departementDTO.getNom());
    }

    @Test
    void toEntity_WithNullDTO_ShouldReturnNull() {
        Departement result = departementMapper.toEntity(null);
        assertThat(result).isNull();
    }
}