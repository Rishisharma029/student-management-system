package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.AttendanceRequestDTO;
import com.example.studentmanagement.dto.AttendanceResponseDTO;
import com.example.studentmanagement.dto.AttendanceSummaryDTO;
import com.example.studentmanagement.exception.AttendanceNotFoundException;
import com.example.studentmanagement.exception.DuplicateAttendanceException;
import com.example.studentmanagement.exception.StudentNotFoundException;
import com.example.studentmanagement.exception.SubjectNotFoundException;
import com.example.studentmanagement.model.Attendance;
import com.example.studentmanagement.model.Attendance.AttendanceStatus;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.model.Subject;
import com.example.studentmanagement.repository.AttendanceRepository;
import com.example.studentmanagement.repository.StudentRepository;
import com.example.studentmanagement.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ═══════════════════════════════════════════════════════════════
 *  AttendanceService — Business logic for attendance
 * ═══════════════════════════════════════════════════════════════
 *
 *  Handles marking attendance, updating it, fetching records,
 *  and calculating attendance percentages.
 *
 *  Key business rule enforced here:
 *  A student cannot be marked twice for the same subject on the same day.
 * ═══════════════════════════════════════════════════════════════
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;


    // ==================================================================
    //  MARK ATTENDANCE
    // ==================================================================

    /**
     * Marks attendance for a student in a subject on a given date.
     *
     * Business rules:
     *  - Student must exist
     *  - Subject must exist
     *  - Attendance cannot already exist for this student/subject/date
     *
     * @param requestDTO  contains studentId, subjectId, date, and status
     * @return            the created attendance record
     */
    @Transactional
    
    // Solution for JAVA-17-service:
    public AttendanceResponseDTO markAttendance(AttendanceRequestDTO requestDTO) {
        log.info("Marking attendance: student={}, subject={}, date={}, status={}",
            requestDTO.getStudentId(),
            requestDTO.getSubjectId(),
            requestDTO.getAttendanceDate(),
            requestDTO.getStatus()
        );

        // Step 1: Verify both student and subject exist
        // These methods throw NotFoundException if not found
        Student student = findStudentEntityOrThrow(requestDTO.getStudentId());
        Subject subject = findSubjectEntityOrThrow(requestDTO.getSubjectId());

        // Step 2: Check for duplicate attendance
        boolean alreadyMarked = attendanceRepository.existsByStudentIdAndSubjectIdAndAttendanceDate(
            requestDTO.getStudentId(),
            requestDTO.getSubjectId(),
            requestDTO.getAttendanceDate()
        );

        if (alreadyMarked) {
            throw new DuplicateAttendanceException(
                "Attendance for student ID " + requestDTO.getStudentId() +
                " in subject ID " + requestDTO.getSubjectId() +
                " on " + requestDTO.getAttendanceDate() + " has already been marked"
            );
        }

        // Step 3: Build the Attendance entity
        Attendance attendance = Attendance.builder()
            .student(student)
            .subject(subject)
            .attendanceDate(requestDTO.getAttendanceDate())
            .status(requestDTO.getStatus())
            .build();

        // Step 4: Save to database
        Attendance saved = attendanceRepository.save(attendance);

        log.info("Attendance marked successfully with ID: {}", saved.getId());
        return mapEntityToResponseDTO(saved);
    }

    // ==================================================================
    //  GET ATTENDANCE BY STUDENT
    // ==================================================================

    /**
     * Returns all attendance records for a specific student.
     * Ordered by date descending (most recent first).
     *
     * @param studentId  the student's ID
     * @return           list of attendance records
     */
    @Transactional(readOnly = true)
    public List<AttendanceResponseDTO> getAttendanceByStudent(Long studentId) {
        // Verify the student exists
        findStudentEntityOrThrow(studentId);

        return attendanceRepository.findByStudentIdOrderByAttendanceDateDesc(studentId)
            .stream()
            .map(this::mapEntityToResponseDTO)
            .collect(Collectors.toList());
    }

    // ==================================================================
    //  GET ATTENDANCE BY SUBJECT
    // ==================================================================

    /**
     * Returns all attendance records for a specific subject.
     *
     * @param subjectId  the subject's ID
     * @return           list of attendance records
     */
    @Transactional(readOnly = true)
    public List<AttendanceResponseDTO> getAttendanceBySubject(Long subjectId) {
        findSubjectEntityOrThrow(subjectId);

        return attendanceRepository.findBySubjectIdOrderByAttendanceDateDesc(subjectId)
            .stream()
            .map(this::mapEntityToResponseDTO)
            .collect(Collectors.toList());
    }

    // ==================================================================
    //  UPDATE ATTENDANCE
    // ==================================================================

    /**
     * Updates the status (PRESENT/ABSENT) of an existing attendance record.
     *
     * @param id      the attendance record ID
     * @param status  the new status
     * @return        the updated attendance record
     */
    @Transactional
    public AttendanceResponseDTO updateAttendance(Long id, AttendanceStatus status) {
        log.info("Updating attendance ID {} to status: {}", id, status);

        Attendance attendance = attendanceRepository.findById(id)
            .orElseThrow(() -> new AttendanceNotFoundException(id));

        attendance.setStatus(status);
        Attendance updated = attendanceRepository.save(attendance);

        return mapEntityToResponseDTO(updated);
    }

    // ==================================================================
    //  DELETE ATTENDANCE
    // ==================================================================

    /**
     * Deletes an attendance record.
     *
     * @param id  the attendance record ID
     */
    @Transactional
    public void deleteAttendance(Long id) {
        log.info("Deleting attendance record with ID: {}", id);

        if (!attendanceRepository.existsById(id)) {
            throw new AttendanceNotFoundException(id);
        }

        attendanceRepository.deleteById(id);
    }

    // ==================================================================
    //  ATTENDANCE PERCENTAGE CALCULATION
    // ==================================================================

    /**
     * Calculates the attendance percentage for a student in a specific subject.
     *
     * Formula:
     *   percentage = (presentCount / totalClasses) * 100
     *
     * Edge cases handled:
     *   - If totalClasses is 0, percentage is 0.0 (avoid division by zero)
     *   - Result is rounded to 2 decimal places
     *
     * @param studentId  the student's ID
     * @param subjectId  the subject's ID
     * @return           AttendanceSummaryDTO with percentage and counts
     */
    @Transactional(readOnly = true)
    
    // Solution for JAVA-18-service:
    public AttendanceSummaryDTO getAttendanceSummary(Long studentId, Long subjectId) {
        // Verify both exist
        Student student = findStudentEntityOrThrow(studentId);
        Subject subject = findSubjectEntityOrThrow(subjectId);

        // Count total classes (all attendance records for this student/subject)
        long totalClasses = attendanceRepository.countByStudentIdAndSubjectId(studentId, subjectId);

        // Count how many times the student was PRESENT
        long presentCount = attendanceRepository.countByStudentIdAndSubjectIdAndStatus(
            studentId, subjectId, AttendanceStatus.PRESENT
        );

        long absentCount = totalClasses - presentCount;

        // Calculate percentage — guard against division by zero
        double percentage = 0.0;
        if (totalClasses > 0) {
            percentage = ((double) presentCount / totalClasses) * 100;
            // Round to 2 decimal places: e.g., 84.666... → 84.67
            percentage = Math.round(percentage * 100.0) / 100.0;
        }

        return AttendanceSummaryDTO.builder()
            .studentId(studentId)
            .studentName(student.getName())
            .studentRollNumber(student.getRollNumber())
            .subjectId(subjectId)
            .subjectName(subject.getName())
            .subjectCode(subject.getCode())
            .totalClasses(totalClasses)
            .presentCount(presentCount)
            .absentCount(absentCount)
            .attendancePercentage(percentage)
            .build();
    }

    /**
     * Calculates the overall attendance percentage across all students and subjects.
     * Used for the dashboard stat card.
     */
    @Transactional(readOnly = true)
    public double getOverallAttendancePercentage() {
        long totalRecords = attendanceRepository.count();
        if (totalRecords == 0) {
            return 0.0;
        }
        long presentRecords = attendanceRepository.countByStatus(AttendanceStatus.PRESENT);
        double percentage = ((double) presentRecords / totalRecords) * 100;
        return Math.round(percentage * 100.0) / 100.0;
    }

    // ==================================================================
    //  PRIVATE HELPERS
    // ==================================================================

    /**
     * Fetches a real Student entity from the database.
     * Throws StudentNotFoundException if the student doesn't exist.
     *
     * We inject StudentRepository directly here because we need the full
     * entity object (with name, rollNumber, etc.) to populate the response DTO.
     */
    private Student findStudentEntityOrThrow(Long studentId) {
        return studentRepository.findById(studentId)
            .orElseThrow(() -> new StudentNotFoundException(studentId));
    }

    /**
     * Fetches a real Subject entity from the database.
     * Throws SubjectNotFoundException if the subject doesn't exist.
     */
    private Subject findSubjectEntityOrThrow(Long subjectId) {
        return subjectRepository.findById(subjectId)
            .orElseThrow(() -> new SubjectNotFoundException(subjectId));
    }

    /**
     * Converts an Attendance entity to AttendanceResponseDTO.
     * Flattens nested student/subject info into the response.
     */
    private AttendanceResponseDTO mapEntityToResponseDTO(Attendance attendance) {
        return AttendanceResponseDTO.builder()
            .id(attendance.getId())
            .studentId(attendance.getStudent().getId())
            .studentName(attendance.getStudent().getName())
            .studentRollNumber(attendance.getStudent().getRollNumber())
            .subjectId(attendance.getSubject().getId())
            .subjectName(attendance.getSubject().getName())
            .subjectCode(attendance.getSubject().getCode())
            .attendanceDate(attendance.getAttendanceDate())
            .status(attendance.getStatus())
            .createdAt(attendance.getCreatedAt())
            .build();
    }
}
