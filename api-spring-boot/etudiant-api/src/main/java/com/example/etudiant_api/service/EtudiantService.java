package com.example.etudiant_api.service;

import com.example.etudiant_api.dto.EtudiantDTO;
import com.example.etudiant_api.entity.Etudiant;
import com.example.etudiant_api.mapper.EtudiantMapper;
import com.example.etudiant_api.repository.EtudiantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EtudiantService {
    private final EtudiantRepository etudiantRepository;
    private final EtudiantMapper etudiantMapper;

    public List<EtudiantDTO> findAll() {
        return etudiantRepository.findAll().stream()
                .map(etudiantMapper::toDto)
                .collect(Collectors.toList());
    }

    public EtudiantDTO findById(Long id) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
        return etudiantMapper.toDto(etudiant);
    }

    public EtudiantDTO save(EtudiantDTO dto) {
        Etudiant etudiant = etudiantMapper.toEntity(dto);
        return etudiantMapper.toDto(etudiantRepository.save(etudiant));
    }

    public EtudiantDTO update(Long id, EtudiantDTO dto) {
        Etudiant existing = etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
        existing.setCin(dto.getCin());
        existing.setNom(dto.getNom());
        existing.setDateNaissance(dto.getDateNaissance());
        existing.setEmail(dto.getEmail());
        existing.setAnneePremiereInscription(dto.getAnneePremiereInscription());
        return etudiantMapper.toDto(etudiantRepository.save(existing));
    }

    public void delete(Long id) {
        etudiantRepository.deleteById(id);
    }

    public List<EtudiantDTO> findByAnnee(int annee) {
        return etudiantRepository.findByAnneePremiereInscription(annee).stream()
                .map(etudiantMapper::toDto)
                .collect(Collectors.toList());
    }
}