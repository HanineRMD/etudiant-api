package com.example.etudiant_api.kafka;

import com.example.etudiant_api.dto.EtudiantDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, EtudiantEvent> kafkaTemplate;
    private static final String TOPIC_ETUDIANT_CREATED = "etudiant-created";

    public void publishEtudiantCreated(EtudiantDTO etudiant) {
        EtudiantEvent event = EtudiantEvent.builder()
                .etudiantId(etudiant.getId())
                .nom(etudiant.getNom())
                .email(etudiant.getEmail())
                .timestamp(LocalDateTime.now())
                .build();

        log.info("📤 Publication événement Kafka - Topic: {}, Etudiant: {}",
                TOPIC_ETUDIANT_CREATED, event.getNom());
        kafkaTemplate.send(TOPIC_ETUDIANT_CREATED, event);
    }
}