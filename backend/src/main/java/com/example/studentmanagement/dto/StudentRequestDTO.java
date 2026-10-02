package com.example.studentmanagement.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ═══════════════════════════════════════════════════════════════
 *  StudentRequestDTO — What we expect when creating/updating a student
 * ═══════════════════════════════════════════════════════════════
 *
 *  DTO = Data Transfer Object.
 *  This class defines the shape of incoming JSON from the frontend.
 *  We NEVER expose the raw JPA entity — we use DTOs instead.
 *
 *  Why DTOs?
 *  - We control exactly what fields come in (security)
 *  - We validate before touching the database
 *  - We can version our API without breaking the entity
 *
 *  Validation annotations:
 *  @NotBlank  → field cannot be null, empty, or whitespace
 *  @Email     → must be valid email format
 *  @Size      → enforces min/max character length
 *  @Min/@Max  → enforces numeric range
 *  @Pattern   → enforces a regex pattern
 * ═══════════════════════════════════════════════════════════════
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    @Pattern(
        regexp = "^[0-9]{10}$",
        message = "Phone number must be exactly 10 digits"
    )
    private String phone;

    @NotBlank(message = "Roll number is required")
    @Size(min = 2, max = 20, message = "Roll number must be between 2 and 20 characters")
    private String rollNumber;

    @NotBlank(message = "Course is required")
    @Size(min = 2, max = 100, message = "Course must be between 2 and 100 characters")
    private String course;

    @NotNull(message = "Semester is required")
    @Min(value = 1, message = "Semester must be at least 1")
    @Max(value = 8, message = "Semester cannot exceed 8")
    private Integer semester;
}
