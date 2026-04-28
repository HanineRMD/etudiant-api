package com.example.grading_service.controller;

import com.example.grading_service.dto.NoteDTO;  // ← Correction: grading_service
import com.example.grading_service.service.NoteService;  // ← Correction: grading_service
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @Operation(summary = "Liste toutes les notes")
    @GetMapping
    public List<NoteDTO> getAll() { return noteService.findAll(); }

    @GetMapping("/{id}")
    public NoteDTO getById(@PathVariable Long id) { return noteService.findById(id); }

    @GetMapping("/etudiant/{studentId}")
    public List<NoteDTO> getByStudent(@PathVariable Long studentId) {
        return noteService.findByStudentId(studentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteDTO create(@RequestBody NoteDTO dto) { return noteService.save(dto); }

    @PutMapping("/{id}")
    public NoteDTO update(@PathVariable Long id, @RequestBody NoteDTO dto) {
        return noteService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { noteService.delete(id); }
}