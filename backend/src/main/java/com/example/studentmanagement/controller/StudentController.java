package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.StudentRequestDTO;
import com.example.studentmanagement.dto.StudentResponseDTO;
import com.example.studentmanagement.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ═══════════════════════════════════════════════════════════════
 *  StudentController — REST endpoints for student management
 * ═══════════════════════════════════════════════════════════════
 *
 *  The controller is a THIN layer. Its only responsibilities:
 *  1. Receive HTTP requests
 *  2. Parse and validate input (with @Valid)
 *  3. Call the service
 *  4. Return the correct HTTP response
 *
 *  NO business logic here. No database calls here.
 *  If you find yourself doing something complex in a controller,
 *  move it to the service.
 *
 *  Annotations:
 *  @RestController    → combines @Controller + @ResponseBody (auto JSON)
 *  @RequestMapping    → all endpoints in this class start with /api/students
 *  @Tag               → Swagger UI group label
 *  @CrossOrigin       → removed; handled globally in CorsConfig
 * ═══════════════════════════════════════════════════════════════
 */
@Slf4j
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Students", description = "Student management endpoints")
public class StudentController {

    private final StudentService studentService;

    // ------------------------------------------------------------------
    //  POST /api/students — Create a new student
    // ------------------------------------------------------------------

    @PostMapping
    @Operation(summary = "Create a new student")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Student created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Email or roll number already exists")
    })
    public ResponseEntity<StudentResponseDTO> createStudent(
            @Valid @RequestBody StudentRequestDTO requestDTO) {

        log.info("POST /api/students - Creating student: {}", requestDTO.getName());

        // @Valid triggers Jakarta Validation — if it fails, GlobalExceptionHandler catches it
        StudentResponseDTO response = studentService.createStudent(requestDTO);

        // Return 201 Created (not 200 OK) — 201 means a resource was created
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ------------------------------------------------------------------
    //  GET /api/students — Get all students (with optional search)
    // ------------------------------------------------------------------

    @GetMapping
    @Operation(summary = "Get all students or search by name/email/roll number")
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents(
            @Parameter(description = "Search term (name, email, or roll number)")
            @RequestParam(required = false) String search,
            @Parameter(description = "Filter by course")
            @RequestParam(required = false) String course,
            @Parameter(description = "Filter by semester")
            @RequestParam(required = false) Integer semester) {

        List<StudentResponseDTO> students;

        if (search != null && !search.isBlank()) {
            // If a search term is provided, use the search query
            log.info("GET /api/students?search={}", search);
            students = studentService.searchStudents(search);
        } else if (course != null && !course.isBlank()) {
            // Filter by course
            students = studentService.getStudentsByCourse(course);
        } else if (semester != null) {
            // Filter by semester
            students = studentService.getStudentsBySemester(semester);
        } else {
            // No filters — return all students
            log.info("GET /api/students - Fetching all students");
            students = studentService.getAllStudents();
        }

        return ResponseEntity.ok(students);
    }

    // ------------------------------------------------------------------
    //  GET /api/students/{id} — Get one student by ID
    // ------------------------------------------------------------------

    @GetMapping("/{id}")
    @Operation(summary = "Get a student by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student found"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<StudentResponseDTO> getStudentById(
            @Parameter(description = "Student ID") @PathVariable Long id) {

        log.info("GET /api/students/{}", id);

        StudentResponseDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    // ------------------------------------------------------------------
    //  PUT /api/students/{id} — Update a student
    // ------------------------------------------------------------------

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing student")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student updated successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found"),
        @ApiResponse(responseCode = "409", description = "Email or roll number conflict")
    })
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @Parameter(description = "Student ID") @PathVariable Long id,
            @Valid @RequestBody StudentRequestDTO requestDTO) {

        log.info("PUT /api/students/{}", id);

        StudentResponseDTO updated = studentService.updateStudent(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    // ------------------------------------------------------------------
    //  DELETE /api/students/{id} — Delete a student
    // ------------------------------------------------------------------

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Student deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<Void> deleteStudent(
            @Parameter(description = "Student ID") @PathVariable Long id) {

        log.info("DELETE /api/students/{}", id);

        studentService.deleteStudent(id);

        // 204 No Content — the delete was successful, nothing to return
        return ResponseEntity.noContent().build();
    }
}
