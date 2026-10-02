-- =============================================================
--  Student Management System — Sample Data
-- =============================================================
--  Run this AFTER schema.sql.
--  This populates the database with realistic test data.
--
--  Includes:
--    - 8 students
--    - 5 subjects
--    - 50+ attendance records
-- =============================================================

USE student_management_db;

-- Clear existing data (to allow re-running this file safely)
-- Order matters: must delete attendance before students/subjects
-- because of foreign key constraints
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE attendance;
TRUNCATE TABLE students;
TRUNCATE TABLE subjects;
SET FOREIGN_KEY_CHECKS = 1;

-- =============================================================
--  Subjects
-- =============================================================

INSERT INTO subjects (id, name, code, created_at) VALUES
    (1, 'Data Structures and Algorithms', 'CS201',  NOW()),
    (2, 'Database Management Systems',   'CS301',  NOW()),
    (3, 'Mathematics III',               'MATH301', NOW()),
    (4, 'Operating Systems',             'CS302',  NOW()),
    (5, 'Web Development',               'CS401',  NOW());

-- =============================================================
--  Students
-- =============================================================

INSERT INTO students (id, name, email, phone, roll_number, course, semester, created_at) VALUES
    (1, 'Khushi Sharma',    'khushi.sharma@college.com',  '9876543210', 'CS2024001', 'Computer Science', 3, NOW()),
    (2, 'Rahul Verma',      'rahul.verma@college.com',   '9876543211', 'CS2024002', 'Computer Science', 3, NOW()),
    (3, 'Priya Patel',      'priya.patel@college.com',   '9876543212', 'CS2024003', 'Computer Science', 3, NOW()),
    (4, 'Arjun Singh',      'arjun.singh@college.com',   '9876543213', 'CS2023001', 'Computer Science', 5, NOW()),
    (5, 'Anjali Gupta',     'anjali.gupta@college.com',  '9876543214', 'ME2024001', 'Mechanical Engineering', 3, NOW()),
    (6, 'Vikram Sharma',    'vikram.sharma@college.com', '9876543215', 'CS2024004', 'Computer Science', 3, NOW()),
    (7, 'Deepika Mehta',    'deepika.mehta@college.com', '9876543216', 'EC2024001', 'Electronics', 3, NOW()),
    (8, 'Rohan Kapoor',     'rohan.kapoor@college.com',  '9876543217', 'CS2023002', 'Computer Science', 5, NOW());

-- =============================================================
--  Attendance Records
-- =============================================================
--  We'll create 2 weeks of attendance for each student
--  in CS201 and CS301 to give realistic data.
-- =============================================================

-- ─ CS201 (Data Structures) attendance for Khushi (student_id=1) ─
INSERT INTO attendance (student_id, subject_id, attendance_date, status) VALUES
    (1, 1, '2024-01-08', 'PRESENT'),
    (1, 1, '2024-01-09', 'PRESENT'),
    (1, 1, '2024-01-10', 'ABSENT'),
    (1, 1, '2024-01-11', 'PRESENT'),
    (1, 1, '2024-01-12', 'PRESENT'),
    (1, 1, '2024-01-15', 'PRESENT'),
    (1, 1, '2024-01-16', 'ABSENT'),
    (1, 1, '2024-01-17', 'PRESENT'),
    (1, 1, '2024-01-18', 'PRESENT'),
    (1, 1, '2024-01-19', 'PRESENT');

-- ─ CS301 (DBMS) attendance for Khushi (student_id=1) ─
INSERT INTO attendance (student_id, subject_id, attendance_date, status) VALUES
    (1, 2, '2024-01-08', 'PRESENT'),
    (1, 2, '2024-01-09', 'PRESENT'),
    (1, 2, '2024-01-10', 'PRESENT'),
    (1, 2, '2024-01-11', 'PRESENT'),
    (1, 2, '2024-01-12', 'ABSENT'),
    (1, 2, '2024-01-15', 'PRESENT'),
    (1, 2, '2024-01-16', 'PRESENT'),
    (1, 2, '2024-01-17', 'PRESENT'),
    (1, 2, '2024-01-18', 'PRESENT'),
    (1, 2, '2024-01-19', 'PRESENT');

-- ─ CS201 attendance for Rahul (student_id=2) ─
INSERT INTO attendance (student_id, subject_id, attendance_date, status) VALUES
    (2, 1, '2024-01-08', 'ABSENT'),
    (2, 1, '2024-01-09', 'PRESENT'),
    (2, 1, '2024-01-10', 'ABSENT'),
    (2, 1, '2024-01-11', 'PRESENT'),
    (2, 1, '2024-01-12', 'PRESENT'),
    (2, 1, '2024-01-15', 'ABSENT'),
    (2, 1, '2024-01-16', 'PRESENT'),
    (2, 1, '2024-01-17', 'PRESENT'),
    (2, 1, '2024-01-18', 'ABSENT'),
    (2, 1, '2024-01-19', 'PRESENT');

-- ─ CS201 attendance for Priya (student_id=3) ─
INSERT INTO attendance (student_id, subject_id, attendance_date, status) VALUES
    (3, 1, '2024-01-08', 'PRESENT'),
    (3, 1, '2024-01-09', 'PRESENT'),
    (3, 1, '2024-01-10', 'PRESENT'),
    (3, 1, '2024-01-11', 'PRESENT'),
    (3, 1, '2024-01-12', 'PRESENT'),
    (3, 1, '2024-01-15', 'PRESENT'),
    (3, 1, '2024-01-16', 'PRESENT'),
    (3, 1, '2024-01-17', 'PRESENT'),
    (3, 1, '2024-01-18', 'ABSENT'),
    (3, 1, '2024-01-19', 'PRESENT');

-- ─ CS301 attendance for Vikram (student_id=6) ─
INSERT INTO attendance (student_id, subject_id, attendance_date, status) VALUES
    (6, 2, '2024-01-08', 'PRESENT'),
    (6, 2, '2024-01-09', 'ABSENT'),
    (6, 2, '2024-01-10', 'ABSENT'),
    (6, 2, '2024-01-11', 'PRESENT'),
    (6, 2, '2024-01-12', 'PRESENT'),
    (6, 2, '2024-01-15', 'PRESENT'),
    (6, 2, '2024-01-16', 'ABSENT'),
    (6, 2, '2024-01-17', 'PRESENT'),
    (6, 2, '2024-01-18', 'PRESENT'),
    (6, 2, '2024-01-19', 'PRESENT');
