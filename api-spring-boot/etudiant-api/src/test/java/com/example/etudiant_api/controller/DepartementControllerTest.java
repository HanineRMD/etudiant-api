package com.example.etudiant_api.controller;

import com.example.etudiant_api.dto.DepartementDTO;
import com.example.etudiant_api.service.DepartementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartementControllerTest {

    @Mock
    private DepartementService departementService;

    @InjectMocks
    private DepartementController departementController;

    private DepartementDTO departementDTO;

    @BeforeEach
    void setUp() {
        departementDTO = new DepartementDTO();
        departementDTO.setId(1L);
        departementDTO.setNom("Informatique");
    }

    @Test
    void getAll_ShouldReturnAllDepartements() {
        List<DepartementDTO> departements = Arrays.asList(departementDTO);
        when(departementService.findAll()).thenReturn(departements);

        List<DepartementDTO> result = departementController.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNom()).isEqualTo("Informatique");
        verify(departementService).findAll();
    }

    @Test
    void getById_ShouldReturnDepartement() {
        when(departementService.findById(1L)).thenReturn(departementDTO);

        DepartementDTO result = departementController.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNom()).isEqualTo("Informatique");
        verify(departementService).findById(1L);
    }

    @Test
    void create_ShouldReturnCreatedDepartement() {
        when(departementService.save(any(DepartementDTO.class))).thenReturn(departementDTO);

        DepartementDTO result = departementController.create(departementDTO);

        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("Informatique");
        verify(departementService).save(departementDTO);
    }

    @Test
    void update_ShouldReturnUpdatedDepartement() {
        when(departementService.update(eq(1L), any(DepartementDTO.class))).thenReturn(departementDTO);

        DepartementDTO result = departementController.update(1L, departementDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(departementService).update(eq(1L), any(DepartementDTO.class));
    }

    @Test
    void delete_ShouldCallService() {
        doNothing().when(departementService).delete(1L);

        departementController.delete(1L);

        verify(departementService).delete(1L);
    }
}