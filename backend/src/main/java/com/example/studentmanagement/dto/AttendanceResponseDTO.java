package com.example.studentmanagement.dto;

import com.example.studentmanagement.model.Attendance.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ═══════════════════════════════════════════════════════════════
 *  AttendanceResponseDTO — What we send back after attendance operations
 * ═══════════════════════════════════════════════════════════════
 *
 *  Notice we include studentName and subjectName — the frontend
 *  shouldn't have to make extra API calls just to display a name.
 * ═══════════════════════════════════════════════════════════════
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponseDTO {

    private Long id;
    private Long studentId;
    private String studentName;   // flattened for convenience
    private String studentRollNumber;
    private Long subjectId;
    private String subjectName;   // flattened for convenience
    private String subjectCode;
    private LocalDate attendanceDate;
    private AttendanceStatus status;
    private LocalDateTime createdAt;
}
