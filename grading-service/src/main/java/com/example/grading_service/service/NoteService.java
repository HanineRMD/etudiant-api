package com.example.grading_service.service;

import com.example.grading_service.client.EtudiantClient;  // ← Vérifiez ce nom
import com.example.grading_service.dto.NoteDTO;
import com.example.grading_service.entity.Note;
import com.example.grading_service.mapper.NoteMapper;
import com.example.grading_service.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final EtudiantClient etudiantClient;  // ← Nom correct

    public List<NoteDTO> findAll() {
        return noteRepository.findAll().stream()
                .map(noteMapper::toDto)
                .collect(Collectors.toList());
    }

    public NoteDTO findById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note non trouvée"));
        return noteMapper.toDto(note);
    }

    public List<NoteDTO> findByStudentId(Long studentId) {
        return noteRepository.findByStudentId(studentId).stream()
                .map(noteMapper::toDto)
                .collect(Collectors.toList());
    }

    public NoteDTO save(NoteDTO dto) {
        try {
            Object etudiant = etudiantClient.getEtudiantById(dto.getStudentId());
            log.info("Étudiant trouvé: {}", etudiant);
        } catch (Exception e) {
            throw new RuntimeException("Étudiant non trouvé avec l'id: " + dto.getStudentId());
        }

        Note note = noteMapper.toEntity(dto);
        return noteMapper.toDto(noteRepository.save(note));
    }

    public NoteDTO update(Long id, NoteDTO dto) {
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

    public Double getAverageByStudentId(Long studentId) {
        List<Note> notes = noteRepository.findByStudentId(studentId);
        if (notes.isEmpty()) return 0.0;
        double sum = notes.stream().mapToDouble(Note::getValeur).sum();
        return sum / notes.size();
    }
}