package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.StudentRequestDTO;
import com.example.studentmanagement.dto.StudentResponseDTO;
import com.example.studentmanagement.exception.DuplicateStudentException;
import com.example.studentmanagement.exception.StudentNotFoundException;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * ═══════════════════════════════════════════════════════════════
 *  StudentServiceTest — Unit tests for StudentService
 * ═══════════════════════════════════════════════════════════════
 *
 *  We use Mockito to MOCK the repository.
 *  This means we test the SERVICE in isolation — no real database needed.
 *
 *  @Mock         → creates a fake version of the class (does nothing by default)
 *  @InjectMocks  → creates the real service and injects the mocks into it
 *  when(...).thenReturn(...) → tells the mock what to return when called
 *  verify(...)   → asserts a method was called
 *  assertThat    → AssertJ fluent assertions (more readable than assertEquals)
 * ═══════════════════════════════════════════════════════════════
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    // Sample data we'll reuse across tests
    private Student sampleStudent;
    private StudentRequestDTO sampleRequestDTO;

    @BeforeEach
    void setUp() {
        // Build a sample student entity
        sampleStudent = Student.builder()
            .id(1L)
            .name("Khushi Sharma")
            .email("khushi.sharma@college.com")
            .phone("9876543210")
            .rollNumber("CS2024001")
            .course("Computer Science")
            .semester(3)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        // Build the matching request DTO
        sampleRequestDTO = StudentRequestDTO.builder()
            .name("Khushi Sharma")
            .email("khushi.sharma@college.com")
            .phone("9876543210")
            .rollNumber("CS2024001")
            .course("Computer Science")
            .semester(3)
            .build();
    }

    // ==================================================================
    //  CREATE STUDENT TESTS
    // ==================================================================

    @Test
    @DisplayName("Should create a student successfully when email and roll number are unique")
    void createStudent_Success() {
        // TODO [JAVA-27]: Write test for createStudent success
    }

    @Test
    @DisplayName("Should throw DuplicateStudentException when email already exists")
    void createStudent_ThrowsException_WhenEmailDuplicate() {
        // TODO [JAVA-28]: Write test for createStudent email duplicate
    }

    @Test
    @DisplayName("Should throw DuplicateStudentException when roll number already exists")
    void createStudent_ThrowsException_WhenRollNumberDuplicate() {
        // TODO [JAVA-29]: Write test for createStudent roll duplicate
    }

    // ==================================================================
    //  GET STUDENT TESTS
    // ==================================================================

    @Test
    @DisplayName("Should return student when valid ID is provided")
    void getStudentById_Success() {
        // TODO [JAVA-30]: Write test for getStudentById success
    }

    @Test
    @DisplayName("Should throw StudentNotFoundException when student ID does not exist")
    void getStudentById_ThrowsException_WhenNotFound() {
        // TODO [JAVA-31]: Write test for getStudentById not found
    }

    @Test
    @DisplayName("Should return all students")
    void getAllStudents_Success() {
        // TODO [JAVA-32]: Write test for getAllStudents success
    }

    // ==================================================================
    //  UPDATE STUDENT TESTS
    // ==================================================================

    @Test
    @DisplayName("Should update student successfully")
    void updateStudent_Success() {
        // TODO [JAVA-33]: Write test for updateStudent success
    }

    // ==================================================================
    //  DELETE STUDENT TESTS
    // ==================================================================

    @Test
    @DisplayName("Should delete student successfully when ID exists")
    void deleteStudent_Success() {
        // TODO [JAVA-34]: Write test for deleteStudent success
    }

    @Test
    @DisplayName("Should throw StudentNotFoundException when deleting non-existent student")
    void deleteStudent_ThrowsException_WhenNotFound() {
        // TODO [JAVA-35]: Write test for deleteStudent not found
    }
}
