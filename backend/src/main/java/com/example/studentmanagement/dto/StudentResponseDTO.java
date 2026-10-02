package com.example.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ═══════════════════════════════════════════════════════════════
 *  StudentResponseDTO — What we send back to the frontend
 * ═══════════════════════════════════════════════════════════════
 *
 *  This is what the API returns when a student is fetched.
 *  Notice we include 'id', 'createdAt', 'updatedAt' here —
 *  fields that only the server knows.
 *
 *  We intentionally do NOT expose internal JPA relationships
 *  (like the list of attendance records) in this response.
 * ═══════════════════════════════════════════════════════════════
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String rollNumber;
    private String course;
    private Integer semester;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
