package com.example.etudiant_api.unit;

import com.example.etudiant_api.dto.EtudiantDTO;
import com.example.etudiant_api.entity.Departement;
import com.example.etudiant_api.entity.Etudiant;
import com.example.etudiant_api.mapper.EtudiantMapper;
import com.example.etudiant_api.repository.DepartementRepository;
import com.example.etudiant_api.repository.EtudiantRepository;
import com.example.etudiant_api.service.EtudiantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private EtudiantMapper etudiantMapper;

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private EtudiantService etudiantService;

    private Etudiant buildEtudiant() {
        Etudiant e = new Etudiant();
        e.setId(1L);
        e.setCin("C123456");
        e.setNom("Ahmed Ben Ali");
        e.setDateNaissance(LocalDate.of(2002, 4, 7));
        e.setEmail("ahmed@test.com");
        e.setAnneePremiereInscription(2021);
        return e;
    }

    private EtudiantDTO buildDTO() {
        EtudiantDTO dto = new EtudiantDTO();
        dto.setId(1L);
        dto.setCin("C123456");
        dto.setNom("Ahmed Ben Ali");
        dto.setDateNaissance(LocalDate.of(2002, 4, 7));
        dto.setEmail("ahmed@test.com");
        dto.setAnneePremiereInscription(2021);
        return dto;
    }

    @Test
    void shouldReturnAllEtudiants() {
        when(etudiantRepository.findAll()).thenReturn(List.of(buildEtudiant()));
        when(etudiantMapper.toDto(any(Etudiant.class))).thenReturn(buildDTO());

        List<EtudiantDTO> result = etudiantService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNom()).isEqualTo("Ahmed Ben Ali");
        verify(etudiantRepository).findAll();
    }

    @Test
    void shouldFindEtudiantById() {
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(buildEtudiant()));
        when(etudiantMapper.toDto(any(Etudiant.class))).thenReturn(buildDTO());

        EtudiantDTO result = etudiantService.findById(1L);

        assertThat(result.getCin()).isEqualTo("C123456");
    }

    @Test
    void shouldThrowWhenEtudiantNotFound() {
        when(etudiantRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> etudiantService.findById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("non trouvé");
    }

    @Test
    void shouldSaveEtudiant() {
        EtudiantDTO dto = buildDTO();
        Etudiant entity = buildEtudiant();

        when(etudiantMapper.toEntity(dto)).thenReturn(entity);
        when(etudiantRepository.save(entity)).thenReturn(entity);
        when(etudiantMapper.toDto(entity)).thenReturn(dto);

        EtudiantDTO result = etudiantService.save(dto);

        assertThat(result.getNom()).isEqualTo("Ahmed Ben Ali");
        verify(etudiantRepository).save(entity);
    }

    @Test
    void shouldDeleteEtudiant() {
        doNothing().when(etudiantRepository).deleteById(1L);

        etudiantService.delete(1L);

        verify(etudiantRepository).deleteById(1L);
    }

    @Test
    void shouldFindByAnnee() {
        when(etudiantRepository.findByAnneePremiereInscription(2021))
                .thenReturn(List.of(buildEtudiant()));
        when(etudiantMapper.toDto(any(Etudiant.class))).thenReturn(buildDTO());

        List<EtudiantDTO> result = etudiantService.findByAnnee(2021);

        assertThat(result).hasSize(1);
    }

    @Test
    void shouldCalculateAge() {
        Etudiant e = buildEtudiant();
        e.setDateNaissance(LocalDate.now().minusYears(23));

        assertThat(e.age()).isEqualTo(23);
    }
}