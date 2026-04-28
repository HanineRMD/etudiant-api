package com.example.grading_service.dto;

import lombok.Data;

@Data
public class NoteDTO {
    private Long id;
    private Long studentId;
    private String matiere;
    private Double valeur;
}