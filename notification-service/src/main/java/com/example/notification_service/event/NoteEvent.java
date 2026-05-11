package com.example.notification.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteEvent {
    private Long noteId;
    private Long studentId;
    private String matiere;
    private Double valeur;
    private LocalDateTime timestamp;
}