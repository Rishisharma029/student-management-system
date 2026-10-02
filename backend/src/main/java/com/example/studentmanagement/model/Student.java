package com.example.studentmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ═══════════════════════════════════════════════════════════════
 *  Student Entity
 * ═══════════════════════════════════════════════════════════════
 *
 *  Maps to the 'students' table in the database.
 *  Each instance of this class = one row in the table.
 *
 *  JPA Annotations quick reference:
 *  @Entity        → tells JPA this is a database table
 *  @Table         → lets us specify the exact table name
 *  @Id            → marks the primary key field
 *  @GeneratedValue → auto-increments the ID
 *  @Column        → customizes column constraints
 *
 *  Lombok Annotations:
 *  @Data          → generates getters, setters, toString, equals, hashCode
 *  @Builder       → lets us use Student.builder().name("x").build()
 *  @NoArgsConstructor → generates empty constructor (required by JPA)
 *  @AllArgsConstructor → generates constructor with all fields
 * ═══════════════════════════════════════════════════════════════
 */
@Entity
@Table(
    name = "students",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email",       name = "uk_student_email"),
        @UniqueConstraint(columnNames = "roll_number", name = "uk_student_roll_number")
    }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    /**
     * Auto-generated primary key.
     * IDENTITY strategy lets MySQL handle auto-increment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Full name of the student.
     * Cannot be null or empty.
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Email address — must be unique across all students.
     * Used for communication and identification.
     */
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    /**
     * Contact phone number.
     */
    @Column(name = "phone", length = 15)
    private String phone;

    /**
     * Roll number assigned to the student by the institution.
     * Must be unique — no two students can share a roll number.
     */
    @Column(name = "roll_number", nullable = false, unique = true, length = 20)
    private String rollNumber;

    /**
     * Course the student is enrolled in.
     * Example: "Computer Science", "Mechanical Engineering"
     */
    @Column(name = "course", nullable = false, length = 100)
    private String course;

    /**
     * Current semester (1 through 8).
     */
    @Column(name = "semester", nullable = false)
    private Integer semester;

    /**
     * Automatically set when the record is first created.
     * We use @CreationTimestamp so Hibernate handles it — we never set this manually.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Automatically updated every time the record changes.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * One student can have many attendance records.
     *
     * mappedBy = "student" → the 'student' field in Attendance owns this relationship
     * cascade = ALL        → if we delete a student, their attendance is also deleted
     * fetch = LAZY         → attendance records are NOT loaded unless we explicitly ask
     *                        (this prevents loading thousands of records by accident)
     * orphanRemoval = true → if an attendance record is removed from this list, delete it
     *
     * JsonIgnore → prevents infinite loop when Jackson serializes:
     *   Student → Attendance → Student → Attendance → ...
     */
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL,
               fetch = FetchType.LAZY, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    @Builder.Default
    private List<Attendance> attendanceRecords = new ArrayList<>();
}
