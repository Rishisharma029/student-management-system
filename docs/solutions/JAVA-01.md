# JAVA-01 — Create Student

## TASK IDENTIFICATION

**Problem:** Creates a new student in the system.

**File:** `StudentService.java`

**Method:** `createStudent`

---

## WHAT PROBLEM DOES THIS SOLVE?

The application needs this feature to fulfill the `createStudent` operation. Without it, the client request cannot be completed, or business validation will fail leading to inconsistent data.

---

## WHY IS THIS NEEDED?

This functionality is required to maintain proper separation of concerns. By implementing this in `StudentService.java`, we ensure that the logic or data access is isolated correctly in its own architectural layer. 
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

1. The operation `createStudent` is invoked.
2. Checks email and roll number uniqueness before saving the student to the DB.
3. The final result is returned (or an exception is thrown based on the application rules if something goes wrong).
4. The caller receives the properly mapped or expected data.

---

## FINAL CODE

```java
public StudentResponseDTO createStudent(StudentRequestDTO requestDTO) {
        log.info("Creating new student with email: {}", requestDTO.getEmail());

        // ── Business Rule: Email must be unique ──
        if (studentRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateStudentException(
                "A student with email '" + requestDTO.getEmail() + "' already exists"
            );
        }

        // ── Business Rule: Roll number must be unique ──
        if (studentRepository.existsByRollNumber(requestDTO.getRollNumber())) {
            throw new DuplicateStudentException(
                "A student with roll number '" + requestDTO.getRollNumber() + "' already exists"
            );
        }

        // Convert the incoming DTO into a Student entity
        Student student = mapRequestDTOToEntity(requestDTO);

        Student savedStudent = studentRepository.save(student);

        log.info("Student created successfully with ID: {}", savedStudent.getId());

        // Convert saved entity back to a response DTO and return
        return mapEntityToResponseDTO(savedStudent);
    }
```

---

## LINE / BLOCK EXPLANATION

- **Method Signature:** Defines the input parameters and return type expected by the system API contract.
- **Logic Validation:** Executes `Checks email and roll number uniqueness before saving the student to the DB.`. This prevents bad data from ever hitting the database.
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

It implements the piece inside `StudentService.java` to bridge the operation correctly.

---

**Difficulty:** Medium
**Why:** Creates a new student in the system.
