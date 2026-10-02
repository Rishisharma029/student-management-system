package com.example.studentmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ═══════════════════════════════════════════════════════════════
 *  SubjectRequestDTO — Input for creating or updating a subject
 * ═══════════════════════════════════════════════════════════════
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectRequestDTO {

    @NotBlank(message = "Subject name is required")
    @Size(min = 2, max = 150, message = "Subject name must be between 2 and 150 characters")
    private String name;

    @NotBlank(message = "Subject code is required")
    @Size(min = 2, max = 20, message = "Subject code must be between 2 and 20 characters")
    private String code;
}
