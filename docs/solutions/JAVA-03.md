# JAVA-03 — Get Student By ID

## 🎓 Khushi, what problem does this solve?
**Problem:** We need to fetch the details of a single specific student using their unique ID.

When building a real Java application, we can't just think about the "happy path". When the user clicks "View Profile" on the frontend, we need to load that specific student. If the student doesn't exist (e.g., deleted or bad URL), we need to gracefully return a 404 Not Found.

---

## 🛠️ Why did we use this specific approach?

We use `findById(id)`. Since it returns an `Optional`, we use `.orElseThrow()` to immediately throw our custom `StudentNotFoundException` if the database comes back empty.

---

## 🧠 Core Java & Spring Concepts Used

- **Optional<T>:** A container object which may or may not contain a non-null value. It forces us to handle the "not found" case.
- **Custom Exceptions:** Throwing a domain-specific exception instead of returning null.

---

## 📝 Step-by-Step Explanation

We call `findStudentOrThrow(id)`, which uses `repository.findById(id)`. If the student exists, we map it to a DTO and return it. If not, the exception is thrown and handled by our GlobalExceptionHandler.

---

## 💻 The Final Code

```java
public StudentResponseDTO getStudentById(Long id) {
        log.debug("Fetching student with ID: {}", id);

        Student student = findStudentOrThrow(id);
        return mapEntityToResponseDTO(student);
    }
```

---

## 🚦 Edge Cases Handled

- The ID doesn't exist in the DB (throws exception).
- The ID is valid (returns DTO).

---

## 🧩 Where does this fit in the app?
Frontend Profile Page -> StudentController -> **StudentService.getStudentById()** -> StudentRepository -> MySQL.
