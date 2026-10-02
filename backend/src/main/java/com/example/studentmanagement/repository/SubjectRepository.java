package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ═══════════════════════════════════════════════════════════════
 *  SubjectRepository
 * ═══════════════════════════════════════════════════════════════
 *
 *  Handles all database operations for subjects.
 *  Inherits standard CRUD operations from JpaRepository.
 * ═══════════════════════════════════════════════════════════════
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    /**
     * Find a subject by its unique code.
     * Used to check for duplicate codes before creating a subject.
     */
    Optional<Subject> findByCode(String code);

    /**
     * Check if a subject with this code already exists.
     */
    boolean existsByCode(String code);

    /**
     * Check if another subject (different ID) has this code.
     * Used when updating a subject — allows keeping the same code.
     */
    boolean existsByCodeAndIdNot(String code, Long id);
}
