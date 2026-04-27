package com.example.etudiant_api.controller;

import com.example.etudiant_api.dto.EtudiantDTO;
import com.example.etudiant_api.service.EtudiantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EtudiantController {
    private final EtudiantService etudiantService;

    @GetMapping
    public List<EtudiantDTO> getAllEtudiants(@RequestParam(required = false) Integer annee) {
        if (annee != null) {
            return etudiantService.findByAnnee(annee);
        }
        return etudiantService.findAll();
    }

    @GetMapping("/{id}")
    public EtudiantDTO getEtudiantById(@PathVariable Long id) {
        return etudiantService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EtudiantDTO createEtudiant(@RequestBody EtudiantDTO etudiantDTO) {
        return etudiantService.save(etudiantDTO);
    }

    @PutMapping("/{id}")
    public EtudiantDTO updateEtudiant(@PathVariable Long id, @RequestBody EtudiantDTO etudiantDTO) {
        return etudiantService.update(id, etudiantDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEtudiant(@PathVariable Long id) {
        etudiantService.delete(id);
    }
}