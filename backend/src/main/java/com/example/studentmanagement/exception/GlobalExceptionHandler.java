package com.example.studentmanagement.exception;

import com.example.studentmanagement.dto.ApiErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * ═══════════════════════════════════════════════════════════════
 *  GlobalExceptionHandler
 * ═══════════════════════════════════════════════════════════════
 *
 *  This class is the central error-handling hub for the entire API.
 *
 *  @RestControllerAdvice → intercepts exceptions from ALL controllers
 *  @ExceptionHandler     → defines which exception this method handles
 *
 *  Without this class, Spring Boot returns ugly HTML error pages
 *  or raw stack traces. With this class, we always return clean
 *  JSON with a consistent structure.
 *
 *  HTTP Status mapping:
 *  400 → Bad Request   (validation errors, invalid input)
 *  404 → Not Found     (student/subject/attendance doesn't exist)
 *  409 → Conflict      (duplicate email, duplicate roll number)
 *  500 → Server Error  (something unexpected happened)
 * ═══════════════════════════════════════════════════════════════
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ─────────────────────────────────────────────────────────────
    //  404 Not Found Handlers
    // ─────────────────────────────────────────────────────────────

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleStudentNotFound(
            StudentNotFoundException ex, HttpServletRequest request) {
        // TODO [JAVA-19]: Handle StudentNotFoundException
        return null;
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleSubjectNotFound(
            SubjectNotFoundException ex, HttpServletRequest request) {
        // TODO [JAVA-20]: Handle SubjectNotFoundException
        return null;
    }

    @ExceptionHandler(AttendanceNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleAttendanceNotFound(
            AttendanceNotFoundException ex, HttpServletRequest request) {
        // TODO [JAVA-21]: Handle AttendanceNotFoundException
        return null;
    }

    // ─────────────────────────────────────────────────────────────
    //  409 Conflict Handlers
    // ─────────────────────────────────────────────────────────────

    @ExceptionHandler(DuplicateStudentException.class)
    public ResponseEntity<ApiErrorDTO> handleDuplicateStudent(
            DuplicateStudentException ex, HttpServletRequest request) {
        // TODO [JAVA-22]: Handle DuplicateStudentException
        return null;
    }

    @ExceptionHandler(DuplicateSubjectException.class)
    public ResponseEntity<ApiErrorDTO> handleDuplicateSubject(
            DuplicateSubjectException ex, HttpServletRequest request) {
        // TODO [JAVA-23]: Handle DuplicateSubjectException
        return null;
    }

    @ExceptionHandler(DuplicateAttendanceException.class)
    public ResponseEntity<ApiErrorDTO> handleDuplicateAttendance(
            DuplicateAttendanceException ex, HttpServletRequest request) {
        // TODO [JAVA-24]: Handle DuplicateAttendanceException
        return null;
    }

    // ─────────────────────────────────────────────────────────────
    //  400 Validation Error Handler
    //  Spring automatically throws this when @Valid fails
    // ─────────────────────────────────────────────────────────────

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleValidationErrors(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        // TODO [JAVA-25]: Handle MethodArgumentNotValidException
        return null;
    }

    // ─────────────────────────────────────────────────────────────
    //  500 Fallback — catches anything we haven't handled above
    // ─────────────────────────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleUnexpected(
            Exception ex, HttpServletRequest request) {
        // TODO [JAVA-26]: Handle Unexpected Exception
        return null;
    }

    // ─────────────────────────────────────────────────────────────
    //  Private helper — builds the ApiErrorDTO consistently
    // ─────────────────────────────────────────────────────────────

    private ResponseEntity<ApiErrorDTO> buildError(
            HttpStatus status,
            String message,
            String path,
            Map<String, String> validationErrors) {

        ApiErrorDTO error = ApiErrorDTO.builder()
            .timestamp(LocalDateTime.now())
            .status(status.value())
            .error(status.getReasonPhrase())
            .message(message)
            .path(path)
            .validationErrors(validationErrors)
            .build();

        return ResponseEntity.status(status).body(error);
    }
}
