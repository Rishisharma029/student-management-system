package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.DashboardStatsDTO;
import com.example.studentmanagement.repository.AttendanceRepository;
import com.example.studentmanagement.repository.StudentRepository;
import com.example.studentmanagement.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ═══════════════════════════════════════════════════════════════
 *  DashboardService — Aggregates statistics for the dashboard
 * ═══════════════════════════════════════════════════════════════
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final AttendanceRepository attendanceRepository;
    private final StudentService studentService;
    private final AttendanceService attendanceService;

    /**
     * Gathers all dashboard statistics in a single call.
     * The frontend makes ONE request and gets everything it needs.
     */
    @Transactional(readOnly = true)
    public DashboardStatsDTO getDashboardStats() {
        log.debug("Computing dashboard statistics");

        long totalStudents   = studentRepository.count();
        long totalSubjects   = subjectRepository.count();
        long totalAttendance = attendanceRepository.count();
        double overallPct    = attendanceService.getOverallAttendancePercentage();

        return DashboardStatsDTO.builder()
            .totalStudents(totalStudents)
            .totalSubjects(totalSubjects)
            .totalAttendanceRecords(totalAttendance)
            .overallAttendancePercentage(overallPct)
            .recentStudents(studentService.getRecentStudents())
            .build();
    }
}
