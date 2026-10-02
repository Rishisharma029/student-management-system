package com.example.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ═══════════════════════════════════════════════════════════════
 *  AttendanceSummaryDTO — Attendance percentage for one student/subject
 * ═══════════════════════════════════════════════════════════════
 *
 *  Example response:
 *  {
 *    "studentId": 1,
 *    "studentName": "Khushi Sharma",
 *    "subjectId": 2,
 *    "subjectName": "Mathematics",
 *    "totalClasses": 50,
 *    "presentCount": 42,
 *    "absentCount": 8,
 *    "attendancePercentage": 84.0
 *  }
 * ═══════════════════════════════════════════════════════════════
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceSummaryDTO {

    private Long studentId;
    private String studentName;
    private String studentRollNumber;
    private Long subjectId;
    private String subjectName;
    private String subjectCode;
    private long totalClasses;
    private long presentCount;
    private long absentCount;
    private double attendancePercentage;
}
