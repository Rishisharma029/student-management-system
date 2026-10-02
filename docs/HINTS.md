# 💡 Hints

This file contains hints to help you think through each task.
These are NOT solutions — they are conceptual guides.

---

## TASK-STUDENT-01 — Get Student by ID

**Concepts to review:**
- `Optional<T>` in Java
- `JpaRepository.findById(id)`
- Method chaining: `.orElseThrow(...)`

**Think about:**
```
findById() returns Optional<Student>.
Optional is a container that either holds a value or is empty.
If it's empty, we throw an exception.

Optional<Student> result = repository.findById(id);
if (result.isEmpty()) {
    throw new StudentNotFoundException(id);
}
return result.get();

// OR, more elegantly:
return repository.findById(id)
    .orElseThrow(() -> new StudentNotFoundException(id));
```

**Expected behavior:**
- ID exists → return the student as DTO
- ID doesn't exist → throw StudentNotFoundException
- Never return null

---

## TASK-STUDENT-02 — Create Student

**Concepts to review:**
- `existsByEmail(String email)` in StudentRepository
- `existsByRollNumber(String rollNumber)` in StudentRepository
- Builder pattern: `Student.builder().name(...).build()`

**Think about:**
```
Step 1: Check for existing email
  if (repo.existsByEmail(dto.getEmail())) {
      throw new DuplicateStudentException("...");
  }

Step 2: Check for existing roll number
  (same pattern)

Step 3: Build entity from DTO
  Student student = Student.builder()
      .name(dto.getName())
      ...
      .build();

Step 4: Save and map to response DTO
```

---

## TASK-STUDENT-03 — Update Student

**Tricky part:** During update, the student is allowed to KEEP
their own email. So you can't use `existsByEmail()` directly
because it would reject a student trying to keep their own email.

**Solution:**
```java
// Does ANY OTHER student have this email?
repository.existsByEmailAndIdNot(newEmail, currentStudentId);
// Returns true ONLY if another student has this email
```

---

## TASK-ATTENDANCE-01 — Mark Attendance

**Concepts to review:**
- `existsByStudentIdAndSubjectIdAndAttendanceDate()`
- `Attendance.builder()` pattern
- Why we link by entity reference, not ID

**Think about:**
```
Step 1: Verify student exists (reuse findStudentOrThrow)
Step 2: Verify subject exists
Step 3: Check for duplicate:
  if (repo.existsByStudentIdAndSubjectIdAndAttendanceDate(...)) {
      throw new DuplicateAttendanceException(...);
  }
Step 4: Build entity:
  Attendance a = Attendance.builder()
      .student(studentEntity)   // JPA entity, not just the ID!
      .subject(subjectEntity)
      .attendanceDate(dto.getAttendanceDate())
      .status(dto.getStatus())
      .build();
Step 5: Save and return DTO
```

---

## TASK-ATTENDANCE-02 — Calculate Percentage

**The math:**
```java
long total   = repo.countByStudentIdAndSubjectId(sId, subId);
long present = repo.countByStudentIdAndSubjectIdAndStatus(sId, subId, PRESENT);

// Guard against division by zero!
double percentage = 0.0;
if (total > 0) {
    percentage = ((double) present / total) * 100;
    // Why cast to double? Because integer division in Java truncates:
    // 42 / 50 = 0 (WRONG!)
    // (double) 42 / 50 = 0.84 (RIGHT)
    percentage = Math.round(percentage * 100.0) / 100.0; // round to 2 decimal places
}
```

**Why (double) matters:**
```java
int a = 42;
int b = 50;
System.out.println(a / b);           // prints 0 (integer division)
System.out.println((double) a / b);  // prints 0.84 (correct!)
```

---

## General Tips

**Don't return null from service methods.**  
Always throw a specific exception when something isn't found.

**Read the repository method names carefully.**  
Spring Data JPA translates method names into SQL:
- `findByEmail` → `WHERE email = ?`
- `existsByEmailAndIdNot` → `WHERE email = ? AND id != ?`
- `countByStudentIdAndSubjectId` → `SELECT COUNT(*) WHERE student_id = ? AND subject_id = ?`

**Use the test output to understand failures.**  
```bash
mvn test -pl backend
```
The test output tells you exactly what's wrong.

**Use Swagger UI to test your endpoints:**  
http://localhost:8080/swagger-ui.html
