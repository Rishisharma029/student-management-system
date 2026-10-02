package com.example.studentmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ═══════════════════════════════════════════════════════════════
 *  Subject Entity
 * ═══════════════════════════════════════════════════════════════
 *
 *  Maps to the 'subjects' table.
 *  A subject is a course like "Mathematics", "Physics", etc.
 *  Each subject has a unique code (e.g., "CS101").
 * ═══════════════════════════════════════════════════════════════
 */
@Entity
@Table(
    name = "subjects",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "code", name = "uk_subject_code")
    }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Full name of the subject.
     * Example: "Data Structures and Algorithms"
     */
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    /**
     * Short unique code for the subject.
     * Example: "CS201", "MATH101"
     */
    @Column(name = "code", nullable = false, unique = true, length = 20)
    private String code;

    /**
     * When this subject was added to the system.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * All attendance records for this subject.
     * LAZY loaded to avoid fetching everything unnecessarily.
     * JsonIgnore to prevent circular serialization.
     */
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL,
               fetch = FetchType.LAZY, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    @Builder.Default
    private List<Attendance> attendanceRecords = new ArrayList<>();
}
