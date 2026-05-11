package com.example.notification.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EtudiantEvent {
    private Long etudiantId;
    private String nom;
    private String email;
    private LocalDateTime timestamp;
}