package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.Attendance;
import com.example.studentmanagement.model.Attendance.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * ═══════════════════════════════════════════════════════════════
 *  AttendanceRepository
 * ═══════════════════════════════════════════════════════════════
 *
 *  Handles all database operations for attendance records.
 *  Contains several custom queries for attendance calculations.
 * ═══════════════════════════════════════════════════════════════
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    /**
     * Get all attendance records for a specific student.
     * Ordered by date descending (most recent first).
     */
    List<Attendance> findByStudentIdOrderByAttendanceDateDesc(Long studentId);

    /**
     * Get all attendance records for a specific subject.
     */
    List<Attendance> findBySubjectIdOrderByAttendanceDateDesc(Long subjectId);

    /**
     * Get all attendance records for a student in a specific subject.
     * Used to calculate attendance percentage for a student per subject.
     */
    List<Attendance> findByStudentIdAndSubjectId(Long studentId, Long subjectId);

    /**
     * Check if attendance already exists for this student/subject/date combination.
     * This is the duplicate attendance prevention check.
     */
    boolean existsByStudentIdAndSubjectIdAndAttendanceDate(
        Long studentId, Long subjectId, LocalDate attendanceDate
    );

    /**
     * Find a specific attendance record (used for updates — to check for
     * duplicate when the record itself already exists).
     */
    Optional<Attendance> findByStudentIdAndSubjectIdAndAttendanceDate(
        Long studentId, Long subjectId, LocalDate attendanceDate
    );

    /**
     * Count how many times a student was PRESENT in a subject.
     * Used in the attendance percentage calculation:
     * percentage = (presentCount / totalClasses) * 100
     */
    long countByStudentIdAndSubjectIdAndStatus(
        Long studentId, Long subjectId, AttendanceStatus status
    );

    /**
     * Count total attendance records for a student in a subject.
     * This is the 'total classes' number.
     */
    long countByStudentIdAndSubjectId(Long studentId, Long subjectId);

    /**
     * Count total PRESENT records across all students.
     * Used for the dashboard overall attendance percentage.
     */
    long countByStatus(AttendanceStatus status);

    /**
     * Get attendance for a student between two dates.
     * Useful for filtering by date range.
     */
    @Query("SELECT a FROM Attendance a WHERE a.student.id = :studentId " +
           "AND a.attendanceDate BETWEEN :startDate AND :endDate " +
           "ORDER BY a.attendanceDate DESC")
    List<Attendance> findByStudentIdAndDateRange(
        @Param("studentId") Long studentId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
}
