package com.example.studentmanagement.exception;

/**
 * Thrown when a requested subject does not exist in the database.
 * Returns HTTP 404 via GlobalExceptionHandler.
 */
public class SubjectNotFoundException extends RuntimeException {

    public SubjectNotFoundException(String message) {
        super(message);
    }

    public SubjectNotFoundException(Long id) {
        super("Subject with ID " + id + " was not found");
    }
}
