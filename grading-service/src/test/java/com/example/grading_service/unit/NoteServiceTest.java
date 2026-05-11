package com.example.grading_service.unit;

import com.example.grading_service.client.EtudiantClient;
import com.example.grading_service.dto.NoteDTO;
import com.example.grading_service.entity.Note;
import com.example.grading_service.mapper.NoteMapper;
import com.example.grading_service.repository.NoteRepository;
import com.example.grading_service.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private NoteMapper noteMapper;

    @Mock
    private EtudiantClient etudiantClient;

    @InjectMocks
    private NoteService noteService;

    private Note buildNote() {
        Note n = new Note();
        n.setId(1L);
        n.setStudentId(10L);
        n.setMatiere("Mathématiques");
        n.setValeur(15.5);
        return n;
    }

    private NoteDTO buildNoteDTO() {
        NoteDTO dto = new NoteDTO();
        dto.setId(1L);
        dto.setStudentId(10L);
        dto.setMatiere("Mathématiques");
        dto.setValeur(15.5);
        return dto;
    }

    @Test
    void shouldReturnAllNotes() {
        when(noteRepository.findAll()).thenReturn(List.of(buildNote()));
        when(noteMapper.toDto(any(Note.class))).thenReturn(buildNoteDTO());

        List<NoteDTO> result = noteService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMatiere()).isEqualTo("Mathématiques");
        verify(noteRepository).findAll();
    }

    @Test
    void shouldFindNoteById() {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(buildNote()));
        when(noteMapper.toDto(any(Note.class))).thenReturn(buildNoteDTO());

        NoteDTO result = noteService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowWhenNoteNotFound() {
        when(noteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> noteService.findById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("non trouvée");
    }

    @Test
    void shouldFindNotesByStudentId() {
        when(noteRepository.findByStudentId(10L)).thenReturn(List.of(buildNote()));
        when(noteMapper.toDto(any(Note.class))).thenReturn(buildNoteDTO());

        List<NoteDTO> result = noteService.findByStudentId(10L);

        assertThat(result).hasSize(1);
        verify(noteRepository).findByStudentId(10L);
    }

    @Test
    void shouldSaveNoteWhenEtudiantExists() {
        NoteDTO dto = buildNoteDTO();
        Note entity = buildNote();

        when(etudiantClient.getEtudiantById(10L)).thenReturn(new Object());
        when(noteMapper.toEntity(dto)).thenReturn(entity);
        when(noteRepository.save(entity)).thenReturn(entity);
        when(noteMapper.toDto(entity)).thenReturn(dto);

        NoteDTO result = noteService.save(dto);

        assertThat(result).isNotNull();
        verify(noteRepository).save(entity);
    }

    @Test
    void shouldThrowWhenEtudiantNotFoundOnSave() {
        NoteDTO dto = buildNoteDTO();

        when(etudiantClient.getEtudiantById(10L))
                .thenThrow(new RuntimeException("404"));

        assertThatThrownBy(() -> noteService.save(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Étudiant non trouvé");
    }

    @Test
    void shouldCalculateAverageMoyenne() {
        Note n1 = buildNote(); n1.setValeur(12.0);
        Note n2 = buildNote(); n2.setValeur(16.0);

        when(noteRepository.findByStudentId(10L)).thenReturn(List.of(n1, n2));

        Double avg = noteService.getAverageByStudentId(10L);

        assertThat(avg).isEqualTo(14.0);
    }

    @Test
    void shouldReturnZeroAverageWhenNoNotes() {
        when(noteRepository.findByStudentId(99L)).thenReturn(List.of());

        Double avg = noteService.getAverageByStudentId(99L);

        assertThat(avg).isEqualTo(0.0);
    }

    @Test
    void shouldDeleteNote() {
        doNothing().when(noteRepository).deleteById(1L);

        noteService.delete(1L);

        verify(noteRepository).deleteById(1L);
    }
}