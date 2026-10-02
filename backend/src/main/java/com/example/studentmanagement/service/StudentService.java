package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.StudentRequestDTO;
import com.example.studentmanagement.dto.StudentResponseDTO;
import com.example.studentmanagement.exception.DuplicateStudentException;
import com.example.studentmanagement.exception.StudentNotFoundException;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ═══════════════════════════════════════════════════════════════
 *  StudentService — All business logic for students
 * ═══════════════════════════════════════════════════════════════
 *
 *  This is the BRAIN of the student feature.
 *
 *  The flow looks like this:
 *  Frontend → Controller → [THIS SERVICE] → Repository → Database
 *
 *  Responsibilities:
 *  - Validate business rules (not just field formats)
 *  - Check for duplicates before saving
 *  - Convert between DTOs and entities
 *  - Call the repository
 *  - Throw meaningful exceptions when something goes wrong
 *

 * ═══════════════════════════════════════════════════════════════
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    // ==================================================================
    //  CREATE STUDENT
    // ==================================================================

    /**
     * Creates a new student in the system.
     *
     * Steps:
     *  1. Check that email is not already taken
     *  2. Check that roll number is not already taken
     *  3. Convert DTO → Entity
     *  4. Save to database
     *  5. Convert Entity → ResponseDTO and return
     *
     * @param requestDTO  the incoming data from the frontend
     * @return            the saved student as a response DTO
     * @throws DuplicateStudentException if email or roll number already exists
     */
    @Transactional
    
    // Solution for JAVA-01:
    public StudentResponseDTO createStudent(StudentRequestDTO requestDTO) {
        log.info("Creating new student with email: {}", requestDTO.getEmail());

        // ── Business Rule: Email must be unique ──
        if (studentRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateStudentException(
                "A student with email '" + requestDTO.getEmail() + "' already exists"
            );
        }

        // ── Business Rule: Roll number must be unique ──
        if (studentRepository.existsByRollNumber(requestDTO.getRollNumber())) {
            throw new DuplicateStudentException(
                "A student with roll number '" + requestDTO.getRollNumber() + "' already exists"
            );
        }

        // Convert the incoming DTO into a Student entity
        Student student = mapRequestDTOToEntity(requestDTO);

        Student savedStudent = studentRepository.save(student);

        log.info("Student created successfully with ID: {}", savedStudent.getId());

        // Convert saved entity back to a response DTO and return
        return mapEntityToResponseDTO(savedStudent);
    }

    // ==================================================================
    //  GET ALL STUDENTS
    // ==================================================================

    /**
     * Returns all students in the system.
     * @return list of all students as response DTOs
     */
    @Transactional(readOnly = true)
    
    // Solution for JAVA-02:
    public List<StudentResponseDTO> getAllStudents() {
        log.debug("Fetching all students");

        return studentRepository.findAll()
            .stream()
            .map(this::mapEntityToResponseDTO)   // convert each entity to DTO
            .collect(Collectors.toList());
    }

    // ==================================================================
    //  GET STUDENT BY ID
    // ==================================================================

    /**
     * Finds a single student by their ID.
     *
     * @param id  the student's ID
     * @return    the student as a response DTO
     * @throws StudentNotFoundException if no student with this ID exists
     */
    @Transactional(readOnly = true)
    
    // Solution for JAVA-03:
    public StudentResponseDTO getStudentById(Long id) {
        log.debug("Fetching student with ID: {}", id);

        Student student = findStudentOrThrow(id);
        return mapEntityToResponseDTO(student);
    }

    // ==================================================================
    //  UPDATE STUDENT
    // ==================================================================

    /**
     * Updates an existing student's information.
     *
     * Steps:
     *  1. Verify the student exists
     *  2. Check that the new email isn't taken by a DIFFERENT student
     *  3. Check that the new roll number isn't taken by a DIFFERENT student
     *  4. Update the entity
     *  5. Save and return
     *
     * @param id          the ID of the student to update
     * @param requestDTO  the new values
     * @return            the updated student as a response DTO
     */
    @Transactional
    
    // Solution for JAVA-04:
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO requestDTO) {
        log.info("Updating student with ID: {}", id);

        // Step 1: Make sure the student exists
        Student existingStudent = findStudentOrThrow(id);

        // Step 2: Check that the email isn't already used by another student
        // The 'AndIdNot' part means: "exclude this student's own ID from the check"
        // This allows a student to keep their own email without getting a conflict error
        if (studentRepository.existsByEmailAndIdNot(requestDTO.getEmail(), id)) {
            throw new DuplicateStudentException(
                "Email '" + requestDTO.getEmail() + "' is already in use by another student"
            );
        }

        // Step 3: Same check for roll number
        if (studentRepository.existsByRollNumberAndIdNot(requestDTO.getRollNumber(), id)) {
            throw new DuplicateStudentException(
                "Roll number '" + requestDTO.getRollNumber() + "' is already in use by another student"
            );
        }

        // Step 4: Apply the new values to the existing entity
        existingStudent.setName(requestDTO.getName());
        existingStudent.setEmail(requestDTO.getEmail());
        existingStudent.setPhone(requestDTO.getPhone());
        existingStudent.setRollNumber(requestDTO.getRollNumber());
        existingStudent.setCourse(requestDTO.getCourse());
        existingStudent.setSemester(requestDTO.getSemester());

        Student updatedStudent = studentRepository.save(existingStudent);

        log.info("Student ID {} updated successfully", id);
        return mapEntityToResponseDTO(updatedStudent);
    }

    // ==================================================================
    //  DELETE STUDENT
    // ==================================================================

    /**
     * Deletes a student by ID.
     * Because of CascadeType.ALL in the Student entity,
     * all attendance records for this student are also deleted.
     *
     * @param id  the ID of the student to delete
     * @throws StudentNotFoundException if no student with this ID exists
     */
    @Transactional
    
    // Solution for JAVA-05:
    public void deleteStudent(Long id) {
        log.info("Deleting student with ID: {}", id);

        // Make sure the student exists before trying to delete
        findStudentOrThrow(id);

        studentRepository.deleteById(id);

        log.info("Student ID {} deleted successfully", id);
    }

    // ==================================================================
    //  SEARCH STUDENTS
    // ==================================================================

    /**
     * Searches for students by name, email, or roll number.
     * Case-insensitive. Partial match supported.
     *
     * @param searchTerm  any part of name, email, or roll number
     * @return            list of matching students
     */
    @Transactional(readOnly = true)
    
    // Solution for JAVA-06:
    public List<StudentResponseDTO> searchStudents(String searchTerm) {
        log.debug("Searching students with term: '{}'", searchTerm);

        if (searchTerm == null || searchTerm.isBlank()) {
            return getAllStudents();
        }

        return studentRepository.searchStudents(searchTerm.trim())
            .stream()
            .map(this::mapEntityToResponseDTO)
            .collect(Collectors.toList());
    }

    /**
     * Filters students by course.
     */
    @Transactional(readOnly = true)
    
    // Solution for JAVA-36:
    public List<StudentResponseDTO> getStudentsByCourse(String course) {
        return studentRepository.findByCourse(course)
            .stream()
            .map(this::mapEntityToResponseDTO)
            .collect(Collectors.toList());
    }

    /**
     * Filters students by semester.
     */
    @Transactional(readOnly = true)
    
    // Solution for JAVA-37:
    public List<StudentResponseDTO> getStudentsBySemester(Integer semester) {
        return studentRepository.findBySemester(semester)
            .stream()
            .map(this::mapEntityToResponseDTO)
            .collect(Collectors.toList());
    }

    /**
     * Returns the 5 most recently added students.
     * Used for the dashboard "Recent Students" widget.
     */
    @Transactional(readOnly = true)
    
    // Solution for JAVA-38:
    public List<StudentResponseDTO> getRecentStudents() {
        return studentRepository.findTop5ByOrderByCreatedAtDesc()
            .stream()
            .map(this::mapEntityToResponseDTO)
            .collect(Collectors.toList());
    }

    // ==================================================================
    //  PRIVATE HELPER METHODS
    // ==================================================================

    /**
     * Finds a student by ID or throws StudentNotFoundException.
     * This pattern is used in multiple methods, so we extract it.
     *
     */
    private Student findStudentOrThrow(Long id) {
        return studentRepository.findById(id)
            .orElseThrow(() -> new StudentNotFoundException(id));
    }

    /**
     * Converts a StudentRequestDTO into a Student entity.
     * This is where incoming data becomes a saveable object.
     *
     * Note: we don't set id, createdAt, or updatedAt — those
     * are handled by JPA automatically.
     */
    private Student mapRequestDTOToEntity(StudentRequestDTO dto) {
        return Student.builder()
            .name(dto.getName().trim())
            .email(dto.getEmail().toLowerCase().trim())
            .phone(dto.getPhone())
            .rollNumber(dto.getRollNumber().trim().toUpperCase())
            .course(dto.getCourse().trim())
            .semester(dto.getSemester())
            .build();
    }

    /**
     * Converts a Student entity into a StudentResponseDTO.
     * This is what gets sent back to the frontend.
     *
     * We never return the raw entity — DTOs give us control
     * over exactly what data leaves the server.
     */
    public StudentResponseDTO mapEntityToResponseDTO(Student student) {
        return StudentResponseDTO.builder()
            .id(student.getId())
            .name(student.getName())
            .email(student.getEmail())
            .phone(student.getPhone())
            .rollNumber(student.getRollNumber())
            .course(student.getCourse())
            .semester(student.getSemester())
            .createdAt(student.getCreatedAt())
            .updatedAt(student.getUpdatedAt())
            .build();
    }
}
