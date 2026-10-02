package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.SubjectRequestDTO;
import com.example.studentmanagement.dto.SubjectResponseDTO;
import com.example.studentmanagement.exception.DuplicateSubjectException;
import com.example.studentmanagement.exception.SubjectNotFoundException;
import com.example.studentmanagement.model.Subject;
import com.example.studentmanagement.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ═══════════════════════════════════════════════════════════════
 *  SubjectService — Business logic for subjects
 * ═══════════════════════════════════════════════════════════════
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Transactional
    public SubjectResponseDTO createSubject(SubjectRequestDTO requestDTO) {
        log.info("Creating new subject with code: {}", requestDTO.getCode());

        String normalizedCode = requestDTO.getCode().trim().toUpperCase();

        if (subjectRepository.existsByCode(normalizedCode)) {
            throw new DuplicateSubjectException(
                "A subject with code '" + normalizedCode + "' already exists"
            );
        }

        Subject subject = Subject.builder()
            .name(requestDTO.getName().trim())
            .code(normalizedCode)
            .build();

        Subject savedSubject = subjectRepository.save(subject);
        log.info("Subject created with ID: {}", savedSubject.getId());

        return mapEntityToResponseDTO(savedSubject);
    }

    @Transactional(readOnly = true)
    public List<SubjectResponseDTO> getAllSubjects() {
        return subjectRepository.findAll()
            .stream()
            .map(this::mapEntityToResponseDTO)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubjectResponseDTO getSubjectById(Long id) {
        Subject subject = findSubjectOrThrow(id);
        return mapEntityToResponseDTO(subject);
    }

    @Transactional
    public SubjectResponseDTO updateSubject(Long id, SubjectRequestDTO requestDTO) {
        log.info("Updating subject with ID: {}", id);

        Subject existingSubject = findSubjectOrThrow(id);
        String normalizedCode = requestDTO.getCode().trim().toUpperCase();

        if (subjectRepository.existsByCodeAndIdNot(normalizedCode, id)) {
            throw new DuplicateSubjectException(
                "Subject code '" + normalizedCode + "' is already in use"
            );
        }

        existingSubject.setName(requestDTO.getName().trim());
        existingSubject.setCode(normalizedCode);

        Subject updatedSubject = subjectRepository.save(existingSubject);
        return mapEntityToResponseDTO(updatedSubject);
    }

    @Transactional
    public void deleteSubject(Long id) {
        log.info("Deleting subject with ID: {}", id);
        findSubjectOrThrow(id);
        subjectRepository.deleteById(id);
    }

    // ── Helpers used by other services (e.g. AttendanceService) ──

    public Subject findSubjectOrThrow(Long id) {
        return subjectRepository.findById(id)
            .orElseThrow(() -> new SubjectNotFoundException(id));
    }

    public SubjectResponseDTO mapEntityToResponseDTO(Subject subject) {
        return SubjectResponseDTO.builder()
            .id(subject.getId())
            .name(subject.getName())
            .code(subject.getCode())
            .createdAt(subject.getCreatedAt())
            .build();
    }
}
