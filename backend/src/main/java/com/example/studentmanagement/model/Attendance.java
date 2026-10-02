package com.example.studentmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ═══════════════════════════════════════════════════════════════
 *  Attendance Entity
 * ═══════════════════════════════════════════════════════════════
 *
 *  Maps to the 'attendance' table.
 *  Records whether a specific student was PRESENT or ABSENT
 *  for a specific subject on a specific date.
 *
 *  Relationships:
 *  - Many attendance records belong to ONE student
 *  - Many attendance records belong to ONE subject
 *
 *  Business rule enforced here:
 *  A student cannot have duplicate attendance for the same
 *  subject on the same date (unique constraint on the table).
 * ═══════════════════════════════════════════════════════════════
 */
@Entity
@Table(
    name = "attendance",
    uniqueConstraints = {
        // Prevents marking attendance twice for same student+subject+date
        @UniqueConstraint(
            columnNames = {"student_id", "subject_id", "attendance_date"},
            name = "uk_attendance_student_subject_date"
        )
    }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The student this attendance record belongs to.
     *
     * @ManyToOne — many attendance records can belong to one student
     * LAZY fetch — don't load the student object unless we need it
     * @JoinColumn — the foreign key column in THIS table is 'student_id'
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    /**
     * The subject this attendance record belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    /**
     * The date attendance was taken.
     * We store only the date (no time) — LocalDate, not LocalDateTime.
     */
    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    /**
     * Whether the student was PRESENT or ABSENT.
     * Stored as a String in the database (VARCHAR).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    private AttendanceStatus status;

    /**
     * When this record was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * ─────────────────────────────────────────────────────────
     *  Attendance Status Enum
     * ─────────────────────────────────────────────────────────
     *  We use an enum instead of plain strings so only valid
     *  values can ever be stored. "PREZENT" or "ABSNT" are
     *  impossible with this approach.
     */
    public enum AttendanceStatus {
        PRESENT,
        ABSENT
    }
}
