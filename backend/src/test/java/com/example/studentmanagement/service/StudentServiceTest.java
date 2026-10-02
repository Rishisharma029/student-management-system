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
    // Solution for JAVA-27:
    
    void createStudent_Success() {
        // ARRANGE: tell the mock repository what to return
        when(studentRepository.existsByEmail(anyString())).thenReturn(false);
        when(studentRepository.existsByRollNumber(anyString())).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(sampleStudent);

        // ACT: call the service method
        StudentResponseDTO result = studentService.createStudent(sampleRequestDTO);

        // ASSERT: verify the result is correct
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Khushi Sharma");
        assertThat(result.getEmail()).isEqualTo("khushi.sharma@college.com");
        assertThat(result.getRollNumber()).isEqualTo("CS2024001");

        // Verify save() was called exactly once
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    @DisplayName("Should throw DuplicateStudentException when email already exists")
    void createStudent_ThrowsException_WhenEmailDuplicate() {
        // ARRANGE: simulate an email that already exists
        when(studentRepository.existsByEmail(anyString())).thenReturn(true);

        // ACT + ASSERT: calling createStudent should throw an exception
        assertThatThrownBy(() -> studentService.createStudent(sampleRequestDTO))
            .isInstanceOf(DuplicateStudentException.class)
            .hasMessageContaining("already exists");

        // Verify we never called save() — it should have stopped at the email check
        verify(studentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw DuplicateStudentException when roll number already exists")
    void createStudent_ThrowsException_WhenRollNumberDuplicate() {
        when(studentRepository.existsByEmail(anyString())).thenReturn(false);
        when(studentRepository.existsByRollNumber(anyString())).thenReturn(true);

        assertThatThrownBy(() -> studentService.createStudent(sampleRequestDTO))
            .isInstanceOf(DuplicateStudentException.class)
            .hasMessageContaining("roll number");

        verify(studentRepository, never()).save(any());
    }

    // ==================================================================
    //  GET STUDENT TESTS
    // ==================================================================

    @Test
    @DisplayName("Should return student when valid ID is provided")
    // Solution for JAVA-30:
    
    void getStudentById_Success() {
        // ARRANGE: mock the repository to return our sample student
        when(studentRepository.findById(1L)).thenReturn(Optional.of(sampleStudent));

        // ACT
        StudentResponseDTO result = studentService.getStudentById(1L);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Khushi Sharma");
        assertThat(result.getSemester()).isEqualTo(3);
    }

    @Test
    @DisplayName("Should throw StudentNotFoundException when student ID does not exist")
    void getStudentById_ThrowsException_WhenNotFound() {
        // ARRANGE: simulate the student not being in the database
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThatThrownBy(() -> studentService.getStudentById(99L))
            .isInstanceOf(StudentNotFoundException.class)
            .hasMessageContaining("99");
    }

    @Test
    @DisplayName("Should return all students")
    // Solution for JAVA-32:
    
    void getAllStudents_Success() {
        // ARRANGE: mock a list with one student
        when(studentRepository.findAll()).thenReturn(List.of(sampleStudent));

        // ACT
        List<StudentResponseDTO> result = studentService.getAllStudents();

        // ASSERT
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Khushi Sharma");
    }

    // ==================================================================
    //  UPDATE STUDENT TESTS
    // ==================================================================

    @Test
    @DisplayName("Should update student successfully")
    // Solution for JAVA-33:
    
    void updateStudent_Success() {
        // ARRANGE
        when(studentRepository.findById(1L)).thenReturn(Optional.of(sampleStudent));
        when(studentRepository.existsByEmailAndIdNot(anyString(), anyLong())).thenReturn(false);
        when(studentRepository.existsByRollNumberAndIdNot(anyString(), anyLong())).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(sampleStudent);

        // ACT
        StudentResponseDTO result = studentService.updateStudent(1L, sampleRequestDTO);

        // ASSERT
        assertThat(result).isNotNull();
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    // ==================================================================
    //  DELETE STUDENT TESTS
    // ==================================================================

    @Test
    @DisplayName("Should delete student successfully when ID exists")
    // Solution for JAVA-34:
    
    void deleteStudent_Success() {
        // ARRANGE
        when(studentRepository.findById(1L)).thenReturn(Optional.of(sampleStudent));

        // ACT
        studentService.deleteStudent(1L);

        // ASSERT: verify deleteById was called
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw StudentNotFoundException when deleting non-existent student")
    void deleteStudent_ThrowsException_WhenNotFound() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.deleteStudent(99L))
            .isInstanceOf(StudentNotFoundException.class);

        // Verify we never actually called delete
        verify(studentRepository, never()).deleteById(any());
    }
}
