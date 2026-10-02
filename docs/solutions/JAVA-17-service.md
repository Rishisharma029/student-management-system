# JAVA-17 — Mark Attendance

## TASK IDENTIFICATION

**Problem:** Marks a student as present or absent.

**File:** `AttendanceService.java`

**Method:** `markAttendance`

---

## WHAT PROBLEM DOES THIS SOLVE?

The application needs this feature to fulfill the `markAttendance` operation. Without it, the client request cannot be completed, or business validation will fail leading to inconsistent data.

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

1. The operation `markAttendance` is invoked.
2. Validates student, subject, and prevents duplicate attendance.
3. The final result is returned (or an exception is thrown based on the application rules if something goes wrong).
4. The caller receives the properly mapped or expected data.

---

## FINAL CODE

```java
public AttendanceResponseDTO markAttendance(AttendanceRequestDTO requestDTO) {
        log.info("Marking attendance: student={}, subject={}, date={}, status={}",
            requestDTO.getStudentId(),
            requestDTO.getSubjectId(),
            requestDTO.getAttendanceDate(),
            requestDTO.getStatus()
        );

        // Step 1: Verify both student and subject exist
        // These methods throw NotFoundException if not found
        Student student = findStudentEntityOrThrow(requestDTO.getStudentId());
        Subject subject = findSubjectEntityOrThrow(requestDTO.getSubjectId());

        // Step 2: Check for duplicate attendance
        boolean alreadyMarked = attendanceRepository.existsByStudentIdAndSubjectIdAndAttendanceDate(
            requestDTO.getStudentId(),
            requestDTO.getSubjectId(),
            requestDTO.getAttendanceDate()
        );

        if (alreadyMarked) {
            throw new DuplicateAttendanceException(
                "Attendance for student ID " + requestDTO.getStudentId() +
                " in subject ID " + requestDTO.getSubjectId() +
                " on " + requestDTO.getAttendanceDate() + " has already been marked"
            );
        }

        // Step 3: Build the Attendance entity
        Attendance attendance = Attendance.builder()
            .student(student)
            .subject(subject)
            .attendanceDate(requestDTO.getAttendanceDate())
            .status(requestDTO.getStatus())
            .build();

        // Step 4: Save to database
        Attendance saved = attendanceRepository.save(attendance);

        log.info("Attendance marked successfully with ID: {}", saved.getId());
        return mapEntityToResponseDTO(saved);
    }
```

---

## LINE / BLOCK EXPLANATION

- **Method Signature:** Defines the input parameters and return type expected by the system API contract.
- **Logic Validation:** Executes `Validates student, subject, and prevents duplicate attendance.`. This prevents bad data from ever hitting the database.
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

**Difficulty:** Medium
**Why:** Marks a student as present or absent.
