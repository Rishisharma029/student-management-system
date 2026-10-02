package com.example.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ═══════════════════════════════════════════════════════════════
 *  DashboardStatsDTO — Summary statistics for the dashboard
 * ═══════════════════════════════════════════════════════════════
 *
 *  Sent to the frontend dashboard so it can display:
 *  - Total students registered
 *  - Total subjects in the system
 *  - Overall attendance percentage across all students
 *  - Recently added students
 * ═══════════════════════════════════════════════════════════════
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {

    private long totalStudents;
    private long totalSubjects;
    private long totalAttendanceRecords;
    private double overallAttendancePercentage;
    private List<StudentResponseDTO> recentStudents;
}
