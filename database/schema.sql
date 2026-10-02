-- =============================================================
--  Student Management System — Database Schema
-- =============================================================
--  Run this file to create the complete database from scratch.
--
--  Tables:
--    1. students   — stores student information
--    2. subjects   — stores subject/course information
--    3. attendance — records daily attendance per student per subject
--
--  Run order: schema.sql first, then sample-data.sql
-- =============================================================

-- Create the database (skip if it already exists)
CREATE DATABASE IF NOT EXISTS student_management_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE student_management_db;

-- =============================================================
--  TABLE: students
-- =============================================================

CREATE TABLE IF NOT EXISTS students (
    -- Primary key: auto-increments with each new student
    id              BIGINT          NOT NULL AUTO_INCREMENT,

    -- Student's full name
    name            VARCHAR(100)    NOT NULL,

    -- Email must be unique across all students
    email           VARCHAR(150)    NOT NULL,

    -- Optional phone number (10 digits)
    phone           VARCHAR(15),

    -- Roll number assigned by the institution
    roll_number     VARCHAR(20)     NOT NULL,

    -- Course the student is enrolled in (e.g., "Computer Science")
    course          VARCHAR(100)    NOT NULL,

    -- Current semester: must be 1 through 8
    semester        INT             NOT NULL,

    -- Audit columns: automatically managed by Hibernate
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME                 DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- ─ Constraints ─────────────────────────────────────────────────────────────────
    PRIMARY KEY (id),

    -- Uniqueness rules: no two students can share an email or roll number
    CONSTRAINT uk_student_email       UNIQUE (email),
    CONSTRAINT uk_student_roll_number UNIQUE (roll_number),

    -- Semester must be between 1 and 8
    CONSTRAINT chk_semester CHECK (semester BETWEEN 1 AND 8)
);

-- =============================================================
--  TABLE: subjects
-- =============================================================

CREATE TABLE IF NOT EXISTS subjects (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(150)    NOT NULL,

    -- Short unique code like "CS101", "MATH201"
    code        VARCHAR(20)     NOT NULL,

    created_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    CONSTRAINT uk_subject_code UNIQUE (code)
);

-- =============================================================
--  TABLE: attendance
-- =============================================================

CREATE TABLE IF NOT EXISTS attendance (
    id                  BIGINT      NOT NULL AUTO_INCREMENT,

    -- Foreign key: which student
    student_id          BIGINT      NOT NULL,

    -- Foreign key: which subject
    subject_id          BIGINT      NOT NULL,

    -- The date attendance was taken (date only, no time)
    attendance_date     DATE        NOT NULL,

    -- PRESENT or ABSENT
    status              VARCHAR(10) NOT NULL,

    created_at          DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    -- A student can only be marked once per subject per day
    CONSTRAINT uk_attendance_student_subject_date
        UNIQUE (student_id, subject_id, attendance_date),

    -- Only valid statuses allowed
    CONSTRAINT chk_attendance_status
        CHECK (status IN ('PRESENT', 'ABSENT')),

    -- ─ Foreign Keys ──────────────────────────────────────────────────────────────

    -- If a student is deleted, delete all their attendance records too
    CONSTRAINT fk_attendance_student
        FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,

    -- If a subject is deleted, delete all attendance records for it too
    CONSTRAINT fk_attendance_subject
        FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE
);

-- =============================================================
--  Indexes for performance
-- =============================================================

-- Speed up lookups by student
CREATE INDEX IF NOT EXISTS idx_attendance_student_id ON attendance(student_id);

-- Speed up lookups by subject
CREATE INDEX IF NOT EXISTS idx_attendance_subject_id ON attendance(subject_id);

-- Speed up lookups by date
CREATE INDEX IF NOT EXISTS idx_attendance_date ON attendance(attendance_date);

-- Speed up student searches by course and semester
CREATE INDEX IF NOT EXISTS idx_student_course   ON students(course);
CREATE INDEX IF NOT EXISTS idx_student_semester ON students(semester);
