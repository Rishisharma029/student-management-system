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
     * Attendance records are lazy-loaded to prevent N+1 issues.
     * JsonIgnore prevents circular serialization.
     */
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL,
               fetch = FetchType.LAZY, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    @Builder.Default
    private List<Attendance> attendanceRecords = new ArrayList<>();
}
