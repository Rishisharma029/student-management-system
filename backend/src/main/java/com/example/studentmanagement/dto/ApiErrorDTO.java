package com.example.studentmanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * ═══════════════════════════════════════════════════════════════
 *  ApiErrorDTO — Consistent error response structure
 * ═══════════════════════════════════════════════════════════════
 *
 *  Every error returned by this API follows this structure:
 *  {
 *    "timestamp": "2024-03-15T10:30:00",
 *    "status": 404,
 *    "error": "Not Found",
 *    "message": "Student with ID 99 was not found",
 *    "path": "/api/students/99",
 *    "validationErrors": null
 *  }
 *
 *  @JsonInclude(NON_NULL) → if a field is null, don't include it in JSON.
 *  This keeps error responses clean when there are no validation errors.
 * ═══════════════════════════════════════════════════════════════
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorDTO {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    /**
     * Only populated for validation errors (HTTP 400).
     * Maps field name → error message.
     * Example: { "email": "Email must be a valid email address" }
     */
    private Map<String, String> validationErrors;
}
