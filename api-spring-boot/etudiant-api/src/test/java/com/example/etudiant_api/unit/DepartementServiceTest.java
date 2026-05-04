package com.example.etudiant_api.unit;

import com.example.etudiant_api.dto.DepartementDTO;
import com.example.etudiant_api.entity.Departement;
import com.example.etudiant_api.mapper.DepartementMapper;
import com.example.etudiant_api.repository.DepartementRepository;
import com.example.etudiant_api.service.DepartementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartementServiceTest {

    @Mock
    private DepartementRepository departementRepository;

    @Mock
    private DepartementMapper departementMapper;

    @InjectMocks
    private DepartementService departementService;

    private Departement buildDept() {
        Departement d = new Departement();
        d.setId(1L);
        d.setNom("Informatique");
        return d;
    }

    private DepartementDTO buildDTO() {
        DepartementDTO dto = new DepartementDTO();
        dto.setId(1L);
        dto.setNom("Informatique");
        return dto;
    }

    @Test
    void shouldReturnAllDepartements() {
        when(departementRepository.findAll()).thenReturn(List.of(buildDept()));
        when(departementMapper.toDto(any(Departement.class))).thenReturn(buildDTO());

        List<DepartementDTO> result = departementService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNom()).isEqualTo("Informatique");
    }

    @Test
    void shouldThrowWhenDepartementNotFound() {
        when(departementRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> departementService.findById(99L))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldSaveDepartement() {
        DepartementDTO dto = buildDTO();
        Departement entity = buildDept();

        when(departementMapper.toEntity(dto)).thenReturn(entity);
        when(departementRepository.save(entity)).thenReturn(entity);
        when(departementMapper.toDto(entity)).thenReturn(dto);

        DepartementDTO result = departementService.save(dto);

        assertThat(result.getNom()).isEqualTo("Informatique");
    }

    @Test
    void shouldDeleteDepartement() {
        doNothing().when(departementRepository).deleteById(1L);

        departementService.delete(1L);

        verify(departementRepository).deleteById(1L);
    }
}