# JAVA-18 — Get Attendance Summary

## TASK IDENTIFICATION

**Problem:** Calculates attendance percentage.

**File:** `AttendanceService.java`

**Method:** `getAttendanceSummary`

---

## WHAT PROBLEM DOES THIS SOLVE?

The application needs this feature to fulfill the `getAttendanceSummary` operation. Without it, the client request cannot be completed, or business validation will fail leading to inconsistent data.

---

## WHY IS THIS NEEDED?

This functionality is required to maintain proper separation of concerns. By implementing this in `AttendanceService.java`, we ensure that the logic or data access is isolated correctly in its own architectural layer. 
For example, keeping database logic inside repositories prevents the controller layer from becoming bloated and violating Single Responsibility.

---

## WHY WAS THIS APPROACH USED?

This approach leverages standard Spring Boot patterns.
- If it is a Service layer, it relies on injected repositories to separate business rules from data access.
- If it is a Repository layer, it relies on Spring Data JPA derived queries to generate SQL automatically.
- If it is an Exception Handler, it leverages Spring's `@ExceptionHandler` to globally intercept errors before they reach the user.

Alternatives like manually writing native SQL queries or handling exceptions individually inside every controller were rejected because they add boilerplate and duplicate logic.

---

## JAVA / SPRING CONCEPTS USED

**Concepts:**
- Dependency Injection (Spring Boot)
- Object-Oriented Encapsulation
- Separation of Concerns
- Optionals & Exception Handling

These concepts ensure the application remains modular, testable, and robust.

---

## SIMPLE IMPLEMENTATION EXPLANATION

1. The operation `getAttendanceSummary` is invoked.
2. Aggregates total classes and present count to calculate a percentage.
3. The final result is returned (or an exception is thrown based on the application rules if something goes wrong).
4. The caller receives the properly mapped or expected data.

---

## FINAL CODE

```java
public AttendanceSummaryDTO getAttendanceSummary(Long studentId, Long subjectId) {
        // Verify both exist
        Student student = findStudentEntityOrThrow(studentId);
        Subject subject = findSubjectEntityOrThrow(subjectId);

        // Count total classes (all attendance records for this student/subject)
        long totalClasses = attendanceRepository.countByStudentIdAndSubjectId(studentId, subjectId);

        // Count how many times the student was PRESENT
        long presentCount = attendanceRepository.countByStudentIdAndSubjectIdAndStatus(
            studentId, subjectId, AttendanceStatus.PRESENT
        );

        long absentCount = totalClasses - presentCount;

        // Calculate percentage — guard against division by zero
        double percentage = 0.0;
        if (totalClasses > 0) {
            percentage = ((double) presentCount / totalClasses) * 100;
            // Round to 2 decimal places: e.g., 84.666... → 84.67
            percentage = Math.round(percentage * 100.0) / 100.0;
        }

        return AttendanceSummaryDTO.builder()
            .studentId(studentId)
            .studentName(student.getName())
            .studentRollNumber(student.getRollNumber())
            .subjectId(subjectId)
            .subjectName(subject.getName())
            .subjectCode(subject.getCode())
            .totalClasses(totalClasses)
            .presentCount(presentCount)
            .absentCount(absentCount)
            .attendancePercentage(percentage)
            .build();
    }
```

---

## LINE / BLOCK EXPLANATION

- **Method Signature:** Defines the input parameters and return type expected by the system API contract.
- **Logic Validation:** Executes `Aggregates total classes and present count to calculate a percentage.`. This prevents bad data from ever hitting the database.
- **Return/Throw:** Completes the flow by handing data back to the caller or aborting the transaction.

---

## EXPECTED BEHAVIOR

- On **valid input**, the operation succeeds and the appropriate data (or void) is returned (HTTP 2xx).
- On **invalid input** or missing data, a dedicated Exception is thrown which the GlobalExceptionHandler maps to a standard HTTP 4xx error API response.

---

## EDGE CASES

- Null or missing parameters provided to the method.
- Database connection failure.
- Duplicate inputs resulting in data constraint violations.
- Empty result sets returning an empty list rather than null.

---

## CONNECTION TO THE APPLICATION

This component sits in the Spring Boot flow:
`React Frontend → Spring Controller → Service → Repository → MySQL`

It implements the piece inside `AttendanceService.java` to bridge the operation correctly.

---

**Difficulty:** Hard
**Why:** Calculates attendance percentage.
