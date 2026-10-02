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
    // TODO [JAVA-11]: Find students by course
    // TODO [JAVA-12]: Find students by semester
    // TODO [JAVA-13]: Check if student exists by email
    // TODO [JAVA-14]: Check if student exists by roll number
    // TODO [JAVA-15]: Check if email exists for other student
    // TODO [JAVA-16]: Check if roll number exists for other student
    // TODO [JAVA-17]: Search students
    // TODO [JAVA-18]: Find top 5 recent students
}
