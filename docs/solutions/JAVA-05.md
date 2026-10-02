# JAVA-05 — Delete Student

## 🎓 Khushi, what problem does this solve?
**Problem:** We need to completely remove a student from the system based on their ID.

When building a real Java application, we can't just think about the "happy path". To maintain data hygiene and allow removal of records. We must also ensure we don't try to delete a student that doesn't exist, which could cause a silent failure or DB error.

---

## 🛠️ Why did we use this specific approach?

First, we check if the student exists using our helper `findStudentOrThrow()`. Once confirmed, we call `repository.deleteById()`.

---

## 🧠 Core Java & Spring Concepts Used

- **Cascade Deletion:** Because the `Student` entity has `CascadeType.ALL` on its attendance records, deleting the student here will *automatically* delete all their attendance records in the database. No orphan records left behind!

---

## 📝 Step-by-Step Explanation

We simply verify the student exists. If they do, we delete them. Because it returns `void`, if no exception is thrown, the controller knows it succeeded and returns a 204 No Content.

---

## 💻 The Final Code

```java
public void deleteStudent(Long id) {
        log.info("Deleting student with ID: {}", id);

        // Make sure the student exists before trying to delete
        findStudentOrThrow(id);

        studentRepository.deleteById(id);

        log.info("Student ID {} deleted successfully", id);
    }
```

---

## 🚦 Edge Cases Handled

- Student doesn't exist (throws StudentNotFoundException).

---

## 🧩 Where does this fit in the app?
Frontend Delete Button -> StudentController -> **StudentService.deleteStudent()** -> StudentRepository -> MySQL.
