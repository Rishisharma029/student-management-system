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

        log.warn("Student not found: {}", ex.getMessage());
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI(), null);
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleSubjectNotFound(
            SubjectNotFoundException ex, HttpServletRequest request) {

        log.warn("Subject not found: {}", ex.getMessage());
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI(), null);
    }

    @ExceptionHandler(AttendanceNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleAttendanceNotFound(
            AttendanceNotFoundException ex, HttpServletRequest request) {

        log.warn("Attendance not found: {}", ex.getMessage());
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI(), null);
    }

    // ─────────────────────────────────────────────────────────────
    //  409 Conflict Handlers
    // ─────────────────────────────────────────────────────────────

    @ExceptionHandler(DuplicateStudentException.class)
    public ResponseEntity<ApiErrorDTO> handleDuplicateStudent(
            DuplicateStudentException ex, HttpServletRequest request) {

        log.warn("Duplicate student: {}", ex.getMessage());
        return buildError(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI(), null);
    }

    @ExceptionHandler(DuplicateSubjectException.class)
    public ResponseEntity<ApiErrorDTO> handleDuplicateSubject(
            DuplicateSubjectException ex, HttpServletRequest request) {

        log.warn("Duplicate subject: {}", ex.getMessage());
        return buildError(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI(), null);
    }

    @ExceptionHandler(DuplicateAttendanceException.class)
    public ResponseEntity<ApiErrorDTO> handleDuplicateAttendance(
            DuplicateAttendanceException ex, HttpServletRequest request) {

        log.warn("Duplicate attendance: {}", ex.getMessage());
        return buildError(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI(), null);
    }

    // ─────────────────────────────────────────────────────────────
    //  400 Validation Error Handler
    //  Spring automatically throws this when @Valid fails
    // ─────────────────────────────────────────────────────────────

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleValidationErrors(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        // Collect all field errors into a map: fieldName → errorMessage
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        log.warn("Validation failed: {}", fieldErrors);
        return buildError(
            HttpStatus.BAD_REQUEST,
            "Validation failed. Please check the fields below.",
            request.getRequestURI(),
            fieldErrors
        );
    }

    // ─────────────────────────────────────────────────────────────
    //  500 Fallback — catches anything we haven't handled above
    // ─────────────────────────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleUnexpected(
            Exception ex, HttpServletRequest request) {

        // Log the full stack trace so we can investigate
        log.error("Unexpected error at [{}]: {}", request.getRequestURI(), ex.getMessage(), ex);

        return buildError(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "An unexpected error occurred. Please contact support.",
            request.getRequestURI(),
            null
        );
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
