# JAVA-04 — Update Student

## TASK IDENTIFICATION

**Problem:** Updates an existing student.

**File:** `StudentService.java`

**Method:** `updateStudent`

---

## WHAT PROBLEM DOES THIS SOLVE?

The application needs this feature to fulfill the `updateStudent` operation. Without it, the client request cannot be completed, or business validation will fail leading to inconsistent data.

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

1. The operation `updateStudent` is invoked.
2. Verifies student exists, checks uniqueness for new email/roll number, and updates.
3. The final result is returned (or an exception is thrown based on the application rules if something goes wrong).
4. The caller receives the properly mapped or expected data.

---

## FINAL CODE

```java
public StudentResponseDTO updateStudent(Long id, StudentRequestDTO requestDTO) {
        log.info("Updating student with ID: {}", id);

        // Step 1: Make sure the student exists
        Student existingStudent = findStudentOrThrow(id);

        // Step 2: Check that the email isn't already used by another student
        // The 'AndIdNot' part means: "exclude this student's own ID from the check"
        // This allows a student to keep their own email without getting a conflict error
        if (studentRepository.existsByEmailAndIdNot(requestDTO.getEmail(), id)) {
            throw new DuplicateStudentException(
                "Email '" + requestDTO.getEmail() + "' is already in use by another student"
            );
        }

        // Step 3: Same check for roll number
        if (studentRepository.existsByRollNumberAndIdNot(requestDTO.getRollNumber(), id)) {
            throw new DuplicateStudentException(
                "Roll number '" + requestDTO.getRollNumber() + "' is already in use by another student"
            );
        }

        // Step 4: Apply the new values to the existing entity
        existingStudent.setName(requestDTO.getName());
        existingStudent.setEmail(requestDTO.getEmail());
        existingStudent.setPhone(requestDTO.getPhone());
        existingStudent.setRollNumber(requestDTO.getRollNumber());
        existingStudent.setCourse(requestDTO.getCourse());
        existingStudent.setSemester(requestDTO.getSemester());

        Student updatedStudent = studentRepository.save(existingStudent);

        log.info("Student ID {} updated successfully", id);
        return mapEntityToResponseDTO(updatedStudent);
    }
```

---

## LINE / BLOCK EXPLANATION

- **Method Signature:** Defines the input parameters and return type expected by the system API contract.
- **Logic Validation:** Executes `Verifies student exists, checks uniqueness for new email/roll number, and updates.`. This prevents bad data from ever hitting the database.
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
**Why:** Updates an existing student.
