package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.AttendanceRequestDTO;
import com.example.studentmanagement.dto.AttendanceResponseDTO;
import com.example.studentmanagement.dto.AttendanceSummaryDTO;
import com.example.studentmanagement.model.Attendance.AttendanceStatus;
import com.example.studentmanagement.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AttendanceController — REST endpoints for attendance management.
 *
 * Endpoints:
 *   POST   /api/attendance                                   → Mark attendance
 *   GET    /api/attendance/student/{studentId}               → Get by student
 *   GET    /api/attendance/subject/{subjectId}               → Get by subject
 *   GET    /api/attendance/summary/{studentId}/{subjectId}   → Attendance %
 *   PUT    /api/attendance/{id}                              → Update status
 *   DELETE /api/attendance/{id}                              → Delete record
 */
@Slf4j
@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance", description = "Attendance management endpoints")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    @Operation(summary = "Mark attendance for a student")
    public ResponseEntity<AttendanceResponseDTO> markAttendance(
            @Valid @RequestBody AttendanceRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(attendanceService.markAttendance(requestDTO));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get all attendance records for a student")
    public ResponseEntity<List<AttendanceResponseDTO>> getAttendanceByStudent(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByStudent(studentId));
    }

    @GetMapping("/subject/{subjectId}")
    @Operation(summary = "Get all attendance records for a subject")
    public ResponseEntity<List<AttendanceResponseDTO>> getAttendanceBySubject(
            @PathVariable Long subjectId) {
        return ResponseEntity.ok(attendanceService.getAttendanceBySubject(subjectId));
    }

    @GetMapping("/summary/{studentId}/{subjectId}")
    @Operation(summary = "Get attendance percentage for a student in a subject")
    public ResponseEntity<AttendanceSummaryDTO> getAttendanceSummary(
            @PathVariable Long studentId,
            @PathVariable Long subjectId) {
        return ResponseEntity.ok(attendanceService.getAttendanceSummary(studentId, subjectId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update attendance status (PRESENT or ABSENT)")
    public ResponseEntity<AttendanceResponseDTO> updateAttendance(
            @PathVariable Long id,
            @RequestParam AttendanceStatus status) {
        return ResponseEntity.ok(attendanceService.updateAttendance(id, status));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an attendance record")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}
