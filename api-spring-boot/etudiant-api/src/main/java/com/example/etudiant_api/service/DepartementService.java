package com.example.etudiant_api.service;

import com.example.etudiant_api.dto.DepartementDTO;
import com.example.etudiant_api.entity.Departement;
import com.example.etudiant_api.mapper.DepartementMapper;
import com.example.etudiant_api.repository.DepartementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartementService {
    private final DepartementRepository departementRepository;
    private final DepartementMapper departementMapper;

    public List<DepartementDTO> findAll() {
        return departementRepository.findAll().stream()
                .map(departementMapper::toDto)
                .collect(Collectors.toList());
    }

    public DepartementDTO findById(Long id) {
        Departement d = departementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Département non trouvé"));
        return departementMapper.toDto(d);
    }

    public DepartementDTO save(DepartementDTO dto) {
        return departementMapper.toDto(
                departementRepository.save(departementMapper.toEntity(dto)));
    }

    public DepartementDTO update(Long id, DepartementDTO dto) {
        Departement existing = departementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Département non trouvé"));
        existing.setNom(dto.getNom());
        return departementMapper.toDto(departementRepository.save(existing));
    }

    public void delete(Long id) {
        departementRepository.deleteById(id);
    }
}