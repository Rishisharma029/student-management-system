package com.example.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * SubjectResponseDTO — What we send back to the frontend
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponseDTO {

    private Long id;
    private String name;
    private String code;
    private LocalDateTime createdAt;
}
