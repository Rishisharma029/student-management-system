# JAVA-01 — Create Student

## 🎓 Khushi, what problem does this solve?
**Problem:** We need a way to add new students to the system, but we must ensure that no two students share the same email or roll number.

When building a real Java application, we can't just think about the "happy path". If we blindly save whatever the frontend sends, our database will throw ugly SQL constraint errors, or worse, we might corrupt our data. We need to validate business rules *before* saving.

---

## 🛠️ Why did we use this specific approach?

We use the repository's `existsBy...` methods to check for duplicates. If a duplicate is found, we throw a custom `DuplicateStudentException`. Only if it passes these checks do we map the DTO to an entity and save it.

---

## 🧠 Core Java & Spring Concepts Used

- **DTOs (Data Transfer Objects):** Separating what the API receives from the database entity.
- **Exception Throwing:** Stopping execution immediately when a business rule fails.
- **@Transactional:** Ensuring the whole operation succeeds or fails as a single unit.

---

## 📝 Step-by-Step Explanation

First, we check if the email exists. If it does, boom, exception. Then we check the roll number. If both checks pass, we convert the `StudentRequestDTO` into a `Student` entity using a helper method, save it via the repository, and convert the saved entity back to a response DTO.

---

## 💻 The Final Code

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

## 🚦 Edge Cases Handled

- Email already exists (handled)
- Roll number already exists (handled)
- Missing fields (handled earlier by `@Valid` in the controller)

---

## 🧩 Where does this fit in the app?
Frontend Form -> StudentController -> **StudentService.createStudent()** -> StudentRepository -> MySQL Database.
