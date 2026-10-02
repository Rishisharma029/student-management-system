package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ═══════════════════════════════════════════════════════════════
 *  StudentRepository
 * ═══════════════════════════════════════════════════════════════
 *
 *  This interface handles ALL database operations for students.
 *  We extend JpaRepository<Student, Long> which gives us these
 *  built-in methods for FREE:
 *
 *    save(student)           → INSERT or UPDATE
 *    findById(id)            → SELECT WHERE id = ?
 *    findAll()               → SELECT ALL
 *    deleteById(id)          → DELETE WHERE id = ?
 *    count()                 → SELECT COUNT(*)
 *    existsById(id)          → SELECT EXISTS(...)
 *
 *  We only need to write custom queries for things JPA can't
 *  figure out from the method name alone.
 *
 *  Spring Data JPA Query Method naming convention:
 *  findBy[FieldName]         → WHERE field_name = ?
 *  findBy[Field]Containing   → WHERE field LIKE '%?%'
 *  findBy[Field]IgnoreCase   → case-insensitive search
 * ═══════════════════════════════════════════════════════════════
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // TODO [JAVA-09]: Find student by email
    // TODO [JAVA-10]: Find student by roll number

    /**
     * Find all students enrolled in a specific course.
     * Example: findByCourse("Computer Science")
     */
    List<Student> findByCourse(String course);

    /**
     * Find all students in a specific semester.
     */
    List<Student> findBySemester(Integer semester);

    /**
     * Check if a student with this email already exists.
     * More efficient than findByEmail because it returns boolean,
     * not the full Student object.
     */
    boolean existsByEmail(String email);

    /**
     * Check if a student with this roll number already exists.
     */
    boolean existsByRollNumber(String rollNumber);

    /**
     * Check if another student (different ID) already has this email.
     * Used during UPDATE to allow keeping the same email,
     * but prevent stealing another student's email.
     *
     * @Query with JPQL (Java Persistence Query Language):
     * Unlike SQL, JPQL uses entity class names and field names
     * (not table/column names).
     */
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END " +
           "FROM Student s WHERE s.email = :email AND s.id != :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);

    /**
     * Check if another student (different ID) already has this roll number.
     * Same idea as existsByEmailAndIdNot — used during updates.
     */
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END " +
           "FROM Student s WHERE s.rollNumber = :rollNumber AND s.id != :id")
    boolean existsByRollNumberAndIdNot(@Param("rollNumber") String rollNumber, @Param("id") Long id);

    /**
     * Search students by name, email, or roll number.
     * The LIKE operator with %:term% means "contains this text".
     * LOWER() makes the search case-insensitive.
     *
     * Example: searchStudents("khushi") will match:
     *   - name: "Khushi Sharma"
     *   - email: "khushi@example.com"
     *   - rollNumber: "KHUSHI001"
     */
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
           "LOWER(s.rollNumber) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<Student> searchStudents(@Param("term") String term);

    /**
     * Fetch the most recently added students, ordered by creation date.
     * Used for the dashboard "Recent Students" section.
     *
     * LIMIT is not standard JPQL — we use Spring Data's Pageable instead.
     */
    List<Student> findTop5ByOrderByCreatedAtDesc();
}
