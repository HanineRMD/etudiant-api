package com.example.etudiant_api.controller;

import com.example.etudiant_api.dto.EtudiantDTO;
import com.example.etudiant_api.service.EtudiantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantControllerTest {

    @Mock
    private EtudiantService etudiantService;

    @InjectMocks
    private EtudiantController etudiantController;

    private EtudiantDTO etudiantDTO;

    @BeforeEach
    void setUp() {
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
    void getAllEtudiants_WithoutAnnee_ShouldReturnAll() {
        List<EtudiantDTO> etudiants = Arrays.asList(etudiantDTO);
        when(etudiantService.findAll()).thenReturn(etudiants);

        List<EtudiantDTO> result = etudiantController.getAllEtudiants(null);

        assertThat(result).hasSize(1);
        verify(etudiantService).findAll();
        verify(etudiantService, never()).findByAnnee(anyInt());
    }

    @Test
    void getAllEtudiants_WithAnnee_ShouldReturnFiltered() {
        List<EtudiantDTO> etudiants = Arrays.asList(etudiantDTO);
        when(etudiantService.findByAnnee(2020)).thenReturn(etudiants);

        List<EtudiantDTO> result = etudiantController.getAllEtudiants(2020);

        assertThat(result).hasSize(1);
        verify(etudiantService).findByAnnee(2020);
        verify(etudiantService, never()).findAll();
    }

    @Test
    void getEtudiantById_ShouldReturnEtudiant() {
        when(etudiantService.findById(1L)).thenReturn(etudiantDTO);

        EtudiantDTO result = etudiantController.getEtudiantById(1L);

        assertThat(result).isNotNull();
        verify(etudiantService).findById(1L);
    }

    @Test
    void createEtudiant_ShouldReturnCreatedEtudiant() {
        when(etudiantService.save(any(EtudiantDTO.class))).thenReturn(etudiantDTO);

        EtudiantDTO result = etudiantController.createEtudiant(etudiantDTO);

        assertThat(result).isNotNull();
        verify(etudiantService).save(etudiantDTO);
    }

    @Test
    void updateEtudiant_ShouldReturnUpdatedEtudiant() {
        when(etudiantService.update(eq(1L), any(EtudiantDTO.class))).thenReturn(etudiantDTO);

        EtudiantDTO result = etudiantController.updateEtudiant(1L, etudiantDTO);

        assertThat(result).isNotNull();
        verify(etudiantService).update(eq(1L), any(EtudiantDTO.class));
    }

    @Test
    void deleteEtudiant_ShouldCallService() {
        doNothing().when(etudiantService).delete(1L);

        etudiantController.deleteEtudiant(1L);

        verify(etudiantService).delete(1L);
    }
}