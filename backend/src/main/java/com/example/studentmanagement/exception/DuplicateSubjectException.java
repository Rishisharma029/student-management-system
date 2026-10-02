package com.example.studentmanagement.exception;

/**
 * Thrown when a subject with the same code already exists.
 * Returns HTTP 409 (Conflict).
 */
public class DuplicateSubjectException extends RuntimeException {

    public DuplicateSubjectException(String message) {
        super(message);
    }
}
