package com.example.grading_service.mapper;  // ✅ Correct

import com.example.grading_service.dto.NoteDTO;  // ← Changement: grading → grading_service
import com.example.grading_service.entity.Note;  // ← Changement: grading → grading_service
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {

    public NoteDTO toDto(Note note) {
        if (note == null) return null;
        NoteDTO dto = new NoteDTO();
        dto.setId(note.getId());
        dto.setStudentId(note.getStudentId());
        dto.setMatiere(note.getMatiere());
        dto.setValeur(note.getValeur());
        return dto;
    }

    public Note toEntity(NoteDTO dto) {
        if (dto == null) return null;
        Note note = new Note();
        note.setId(dto.getId());
        note.setStudentId(dto.getStudentId());
        note.setMatiere(dto.getMatiere());
        note.setValeur(dto.getValeur());
        return note;
    }
}