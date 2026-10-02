package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.SubjectRequestDTO;
import com.example.studentmanagement.dto.SubjectResponseDTO;
import com.example.studentmanagement.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SubjectController — REST endpoints for subject management.
 *
 * Endpoints:
 *   POST   /api/subjects         → Create subject
 *   GET    /api/subjects         → Get all subjects
 *   GET    /api/subjects/{id}    → Get subject by ID
 *   PUT    /api/subjects/{id}    → Update subject
 *   DELETE /api/subjects/{id}    → Delete subject
 */
@Slf4j
@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
@Tag(name = "Subjects", description = "Subject management endpoints")
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    @Operation(summary = "Create a new subject")
    public ResponseEntity<SubjectResponseDTO> createSubject(
            @Valid @RequestBody SubjectRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(subjectService.createSubject(requestDTO));
    }

    @GetMapping
    @Operation(summary = "Get all subjects")
    public ResponseEntity<List<SubjectResponseDTO>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a subject by ID")
    public ResponseEntity<SubjectResponseDTO> getSubjectById(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.getSubjectById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing subject")
    public ResponseEntity<SubjectResponseDTO> updateSubject(
            @PathVariable Long id,
            @Valid @RequestBody SubjectRequestDTO requestDTO) {
        return ResponseEntity.ok(subjectService.updateSubject(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a subject by ID")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
}
