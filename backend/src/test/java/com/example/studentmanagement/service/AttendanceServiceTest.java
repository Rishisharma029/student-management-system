package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.AttendanceSummaryDTO;
import com.example.studentmanagement.model.Attendance.AttendanceStatus;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.model.Subject;
import com.example.studentmanagement.repository.AttendanceRepository;
import com.example.studentmanagement.repository.StudentRepository;
import com.example.studentmanagement.repository.SubjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * ═══════════════════════════════════════════════════════════════
 *  AttendanceServiceTest — Unit tests for attendance percentage calculation
 * ═══════════════════════════════════════════════════════════════
 *
 *  These tests verify the attendance percentage calculation logic
 *  including the important division-by-zero edge case.
 * ═══════════════════════════════════════════════════════════════
 */
@ExtendWith(MockitoExtension.class)
class AttendanceServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private AttendanceService attendanceService;

    @Test
    @DisplayName("Should calculate attendance percentage correctly: 42 present / 50 total = 84%")
    void calculateAttendanceSummary_CorrectPercentage() {
        // ARRANGE
        Long studentId = 1L;
        Long subjectId = 1L;

        // Build real entity objects so the DTO can pick up name and rollNumber
        Student student = Student.builder()
            .id(studentId)
            .name("Khushi Sharma")
            .rollNumber("CS2024001")
            .build();

        Subject subject = Subject.builder()
            .id(subjectId)
            .name("Data Structures")
            .code("CS201")
            .build();

        // Mock repository responses
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));

        // 50 total classes, 42 present
        when(attendanceRepository.countByStudentIdAndSubjectId(studentId, subjectId)).thenReturn(50L);
        when(attendanceRepository.countByStudentIdAndSubjectIdAndStatus(
            studentId, subjectId, AttendanceStatus.PRESENT)
        ).thenReturn(42L);

        // ACT
        AttendanceSummaryDTO summary = attendanceService.getAttendanceSummary(studentId, subjectId);

        // ASSERT
        assertThat(summary).isNotNull();
        assertThat(summary.getStudentName()).isEqualTo("Khushi Sharma");
        assertThat(summary.getSubjectName()).isEqualTo("Data Structures");
        assertThat(summary.getTotalClasses()).isEqualTo(50);
        assertThat(summary.getPresentCount()).isEqualTo(42);
        assertThat(summary.getAbsentCount()).isEqualTo(8);

        // Core formula: (42 / 50) * 100 = 84.0
        assertThat(summary.getAttendancePercentage()).isEqualTo(84.0);
    }

    @Test
    @DisplayName("Should return 0.0% when no classes have been recorded (avoid division by zero)")
    void calculateAttendanceSummary_ZeroClasses_ReturnsZeroPercent() {
        Long studentId = 1L;
        Long subjectId = 1L;

        Student student = Student.builder()
            .id(studentId)
            .name("Khushi Sharma")
            .rollNumber("CS2024001")
            .build();

        Subject subject = Subject.builder()
            .id(subjectId)
            .name("Data Structures")
            .code("CS201")
            .build();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));

        // 0 total classes recorded yet
        when(attendanceRepository.countByStudentIdAndSubjectId(studentId, subjectId)).thenReturn(0L);
        when(attendanceRepository.countByStudentIdAndSubjectIdAndStatus(
            studentId, subjectId, AttendanceStatus.PRESENT)
        ).thenReturn(0L);

        AttendanceSummaryDTO summary = attendanceService.getAttendanceSummary(studentId, subjectId);

        assertThat(summary.getTotalClasses()).isEqualTo(0);
        assertThat(summary.getPresentCount()).isEqualTo(0);
        // Critical edge case: no division by zero
        assertThat(summary.getAttendancePercentage()).isEqualTo(0.0);
    }
}
