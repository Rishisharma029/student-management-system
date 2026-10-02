package com.example.studentmanagement.exception;

/**
 * ═══════════════════════════════════════════════════════════════
 *  DuplicateStudentException
 * ═══════════════════════════════════════════════════════════════
 *
 *  Thrown when someone tries to create a student with an email
 *  or roll number that already exists in the system.
 *  Returns HTTP 409 (Conflict) via GlobalExceptionHandler.
 * ═══════════════════════════════════════════════════════════════
 */
public class DuplicateStudentException extends RuntimeException {

    public DuplicateStudentException(String message) {
        super(message);
    }
}
