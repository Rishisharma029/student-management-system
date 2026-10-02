package com.example.studentmanagement.exception;

/**
 * Thrown when a specific attendance record cannot be found.
 * Returns HTTP 404 via GlobalExceptionHandler.
 */
public class AttendanceNotFoundException extends RuntimeException {

    public AttendanceNotFoundException(String message) {
        super(message);
    }

    public AttendanceNotFoundException(Long id) {
        super("Attendance record with ID " + id + " was not found");
    }
}
