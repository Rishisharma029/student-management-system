# JAVA-04 — Update Student

## 🎓 Khushi, what problem does this solve?
**Problem:** We need to update a student's details. But wait—what if they try to change their email to an email that *another* student is already using?

When building a real Java application, we can't just think about the "happy path". Updates are tricky. We have to ensure the student actually exists first. Then, we must ensure any uniqueness constraints (email, roll number) aren't violated by the *new* values, without falsely flagging the student's *own* existing values as duplicates.

---

## 🛠️ Why did we use this specific approach?

We fetch the existing student. We then check `existsByEmailAndIdNot` and `existsByRollNumberAndIdNot`. This brilliantly asks the DB: "Does this email exist for any student EXCEPT this one?"

---

## 🧠 Core Java & Spring Concepts Used

- **Entity State Management:** We modify the fetched entity and call `save()`. Hibernate sees the changes and runs an SQL UPDATE.
- **Exclusion Queries:** Using `AndIdNot` in JPA to exclude the current record from uniqueness checks.

---

## 📝 Step-by-Step Explanation

1. Fetch the student (or throw 404). 2. Check if the new email belongs to *another* student (throw 409 if so). 3. Do the same for roll number. 4. Update the entity's fields. 5. Save and return the mapped DTO.

---

## 💻 The Final Code

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

## 🚦 Edge Cases Handled

- Trying to update a non-existent student (404).
- Changing email to one used by someone else (409).
- Changing email to their own existing email (allowed, because of `AndIdNot`).

---

## 🧩 Where does this fit in the app?
Frontend Edit Form -> StudentController -> **StudentService.updateStudent()** -> StudentRepository -> MySQL.
