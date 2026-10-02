# 📋 Implementation Tasks

Welcome! This document contains your implementation tasks.

The project is already set up and running. Your job is to implement
the missing business logic marked with `// TODO [TASK-XXX]` comments.

**Goal:** Turn every TODO into working Java code.

---

## 📌 How to Work

1. Read the task description completely before writing code
2. Look at the related test in the test folder — it tells you exactly what's expected
3. Read the hint for the task in `docs/HINTS.md`
4. Implement the code
5. Run the tests to verify
6. Test via Swagger UI or the frontend

---

## TASK-STUDENT-01: Get Student by ID

**File:** `StudentService.java`  
**Method:** `getStudentById(Long id)`  
**Status:** ❌ TODO

**What to implement:**
```
Retrieve the Student entity using the supplied ID.
If no record exists, throw StudentNotFoundException.
Convert the entity to StudentResponseDTO and return it.
```

**Expected behavior:**
- `GET /api/students/1` → returns the student with ID 1
- `GET /api/students/999` → returns HTTP 404 with error message

---

## TASK-STUDENT-02: Create Student (Duplicate Check)

**File:** `StudentService.java`  
**Method:** `createStudent(StudentRequestDTO)`  
**Status:** ❌ TODO

**What to implement:**
```
Before saving, check if the email is already taken.
Before saving, check if the roll number is already taken.
If either exists, throw DuplicateStudentException with a clear message.
If both are unique, save the student and return the response DTO.
```

**Business rules:**
- Email must be globally unique
- Roll number must be globally unique
- Email should be stored in lowercase
- Roll number should be stored in uppercase

---

## TASK-STUDENT-03: Update Student

**File:** `StudentService.java`  
**Method:** `updateStudent(Long id, StudentRequestDTO)`  
**Status:** ❌ TODO

**What to implement:**
```
Verify the student exists (throw StudentNotFoundException if not).
Check the new email isn't already used by a DIFFERENT student.
Check the new roll number isn't already used by a DIFFERENT student.
Update the student's fields.
Save and return the updated DTO.
```

**Hint:** There's a difference between:
- "Is this email taken at all?" (use for CREATE)
- "Is this email taken by someone OTHER than this student?" (use for UPDATE)

---

## TASK-STUDENT-04: Delete Student

**File:** `StudentService.java`  
**Method:** `deleteStudent(Long id)`  
**Status:** ❌ TODO

**What to implement:**
```
Verify the student exists before trying to delete.
If not found, throw StudentNotFoundException.
Delete the student.
All attendance records for this student should also be deleted
(this is handled automatically by CascadeType.ALL in the entity).
```

---

## TASK-ATTENDANCE-01: Mark Attendance

**File:** `AttendanceService.java`  
**Method:** `markAttendance(AttendanceRequestDTO)`  
**Status:** ❌ TODO

**What to implement:**
```
Verify the student exists.
Verify the subject exists.
Check that attendance for this student + subject + date doesn't already exist.
If it does, throw DuplicateAttendanceException.
Build the Attendance entity and save it.
Return the response DTO.
```

**Business rule:**
A student can only have ONE attendance record per subject per day.
Marking them twice on the same day must be rejected.

---

## TASK-ATTENDANCE-02: Calculate Attendance Percentage

**File:** `AttendanceService.java`  
**Method:** `getAttendanceSummary(Long studentId, Long subjectId)`  
**Status:** ❌ TODO

**What to implement:**
```
Fetch the total number of attendance records for this student + subject.
Fetch the number of PRESENT records.
Calculate: percentage = (presentCount / totalClasses) * 100
Handle the edge case: if totalClasses == 0, percentage = 0.0
Round to 2 decimal places.
Return an AttendanceSummaryDTO.
```

**Formula:**
```
Present = 42
Total   = 50
Percentage = (42 / 50) × 100 = 84.0%
```

**Edge cases:**
- 0 total classes → return 0.0 (do not divide by zero)
- 0 present out of 10 → return 0.0
- 10 present out of 10 → return 100.0

---

## TASK-REPO-01: Search Students Query

**File:** `StudentRepository.java`  
**Method:** `searchStudents(String term)`  
**Status:** ❌ TODO (JPQL query already written, understand how it works)

**What to understand:**
```sql
SELECT s FROM Student s WHERE
  LOWER(s.name) LIKE LOWER(CONCAT('%', :term, '%')) OR
  LOWER(s.email) LIKE LOWER(CONCAT('%', :term, '%')) OR
  LOWER(s.rollNumber) LIKE LOWER(CONCAT('%', :term, '%'))
```

This is JPQL — not SQL. Entity names, not table names.
`LOWER()` makes it case-insensitive.
`CONCAT('%', :term, '%')` adds wildcards for partial match.

---

## TASK-EXCEPTION-01: Handle Validation Errors

**File:** `GlobalExceptionHandler.java`  
**Method:** `handleValidationErrors`  
**Status:** ❌ TODO (handler already written, understand what it does)

**What to understand:**
```
When @Valid fails on a DTO, Spring throws MethodArgumentNotValidException.
The handler collects all field errors into a Map<fieldName, errorMessage>.
Returns 400 Bad Request with the map in the response.
```

**Test it:**
1. `POST /api/students` with an empty body
2. You should get HTTP 400 with a list of all missing fields

---

## TASK-TEST-01: Write Student Service Test

**File:** `StudentServiceTest.java`  
**Status:** ❌ TODO (tests already written, make them pass)

**What to do:**
```
Implement the StudentService methods so all tests pass.
Run: mvn test -pl backend
```

---

## ✅ Completion Checklist

```
[ ] TASK-STUDENT-01 — Get student by ID
[ ] TASK-STUDENT-02 — Create student (duplicate check)
[ ] TASK-STUDENT-03 — Update student
[ ] TASK-STUDENT-04 — Delete student
[ ] TASK-ATTENDANCE-01 — Mark attendance
[ ] TASK-ATTENDANCE-02 — Calculate attendance %
[ ] TASK-REPO-01 — Understand search query
[ ] TASK-EXCEPTION-01 — Understand exception handler
[ ] TASK-TEST-01 — All tests passing
[ ] Swagger UI tested manually
[ ] Frontend working end-to-end
```
