package com.example.studentmanagement.exception;

/**
 * ═══════════════════════════════════════════════════════════════
 *  StudentNotFoundException
 * ═══════════════════════════════════════════════════════════════
 *
 *  Thrown when a requested student does not exist in the database.
 *  The GlobalExceptionHandler catches this and returns HTTP 404.
 *
 *  RuntimeException means we don't need to declare it with 'throws'.
 *  It propagates automatically up the call stack.
 * ═══════════════════════════════════════════════════════════════
 */
public class StudentNotFoundException extends RuntimeException {

    /**
     * Creates the exception with a descriptive message.
     *
     * Example usage:
     *   throw new StudentNotFoundException("Student with ID 42 was not found");
     *   throw new StudentNotFoundException(id);
     */
    public StudentNotFoundException(String message) {
        super(message);
    }

    /**
     * Convenience constructor — takes just the ID.
     * Automatically creates a readable message.
     */
    public StudentNotFoundException(Long id) {
        super("Student with ID " + id + " was not found");
    }
}
