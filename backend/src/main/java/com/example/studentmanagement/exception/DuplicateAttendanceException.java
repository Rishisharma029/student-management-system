package com.example.studentmanagement.exception;

/**
 * ═══════════════════════════════════════════════════════════════
 *  DuplicateAttendanceException
 * ═══════════════════════════════════════════════════════════════
 *
 *  Thrown when attendance for a student + subject + date
 *  combination already exists.
 *
 *  Business rule: A student can only be marked once per subject
 *  per day — you can't mark Khushi present AND absent on the
 *  same day for the same class.
 *
 *  Returns HTTP 409 (Conflict).
 * ═══════════════════════════════════════════════════════════════
 */
public class DuplicateAttendanceException extends RuntimeException {

    public DuplicateAttendanceException(String message) {
        super(message);
    }
}
