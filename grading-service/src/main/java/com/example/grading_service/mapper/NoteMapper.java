package com.example.grading_service.mapper;

import com.example.grading.dto.NoteDTO;
import com.example.grading.entity.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {

    public NoteDTO toDto(Note n) {
        NoteDTO dto = new NoteDTO();
        dto.setId(n.getId());
        dto.setStudentId(n.getStudentId());
        dto.setMatiere(n.getMatiere());
        dto.setValeur(n.getValeur());
        return dto;
    }

    public Note toEntity(NoteDTO dto) {
        Note n = new Note();
        n.setId(dto.getId());
        n.setStudentId(dto.getStudentId());
        n.setMatiere(dto.getMatiere());
        n.setValeur(dto.getValeur());
        return n;
    }
}