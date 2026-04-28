package com.example.grading_service.service;

import com.example.grading_service.client.EtudiantClient;
import com.example.grading_service.dto.NoteDTO;
import com.example.grading_service.entity.Note;
import com.example.grading_service.mapper.NoteMapper;
import com.example.grading_service.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final EtudiantClient etudiantClient;  // ✅ Ajouté ici

    public List<NoteDTO> findAll() {
        return noteRepository.findAll().stream()
                .map(noteMapper::toDto).collect(Collectors.toList());
    }

    public NoteDTO findById(Long id) {
        Note n = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note non trouvée"));
        return noteMapper.toDto(n);
    }

    public List<NoteDTO> findByStudentId(Long studentId) {
        // ✅ Vérifie que l'étudiant existe
        try {
            etudiantClient.getEtudiantById(studentId);
        } catch (Exception e) {
            throw new RuntimeException("Étudiant introuvable avec id: " + studentId);
        }

        return noteRepository.findByStudentId(studentId).stream()
                .map(noteMapper::toDto).collect(Collectors.toList());
    }

    public NoteDTO save(NoteDTO dto) {
        // ✅ Vérifie que l'étudiant existe avant de sauvegarder
        try {
            etudiantClient.getEtudiantById(dto.getStudentId());
        } catch (Exception e) {
            throw new RuntimeException("Étudiant introuvable avec id: " + dto.getStudentId());
        }

        return noteMapper.toDto(noteRepository.save(noteMapper.toEntity(dto)));
    }

    public NoteDTO update(Long id, NoteDTO dto) {
        // ✅ Vérifie que l'étudiant existe
        try {
            etudiantClient.getEtudiantById(dto.getStudentId());
        } catch (Exception e) {
            throw new RuntimeException("Étudiant introuvable avec id: " + dto.getStudentId());
        }

        Note existing = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note non trouvée"));
        existing.setStudentId(dto.getStudentId());
        existing.setMatiere(dto.getMatiere());
        existing.setValeur(dto.getValeur());
        return noteMapper.toDto(noteRepository.save(existing));
    }

    public void delete(Long id) {
        noteRepository.deleteById(id);
    }
}