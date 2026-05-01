package com.example.etudiant_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class EtudiantDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String cin;
    private String nom;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateNaissance;

    private String email;
    private int anneePremiereInscription;
    private Long departementId;
    private String departementNom;
}