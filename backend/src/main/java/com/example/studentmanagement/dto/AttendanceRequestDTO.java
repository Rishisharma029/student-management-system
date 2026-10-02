package com.example.studentmanagement.dto;

import com.example.studentmanagement.model.Attendance.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * ═══════════════════════════════════════════════════════════════
 *  AttendanceRequestDTO — Input for marking attendance
 * ═══════════════════════════════════════════════════════════════
 *
 *  The frontend sends:
 *  {
 *    "studentId": 1,
 *    "subjectId": 2,
 *    "attendanceDate": "2024-03-15",
 *    "status": "PRESENT"
 *  }
 * ═══════════════════════════════════════════════════════════════
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRequestDTO {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotNull(message = "Attendance date is required")
    private LocalDate attendanceDate;

    @NotNull(message = "Attendance status is required (PRESENT or ABSENT)")
    private AttendanceStatus status;
}
