# JAVA-02 — Get All Students

## 🎓 Khushi, what problem does this solve?
**Problem:** The frontend needs to display a table of all registered students.

When building a real Java application, we can't just think about the "happy path". We need a simple endpoint to fetch all records and convert them into safe DTOs before sending them over the network.

---

## 🛠️ Why did we use this specific approach?

We use the built-in `findAll()` method provided by Spring Data JPA's `JpaRepository`. We then stream over the list of entities and map each one to a `StudentResponseDTO`.

---

## 🧠 Core Java & Spring Concepts Used

- **Java Streams:** Used to cleanly transform a List of Entities into a List of DTOs (`stream().map(...).collect(...)`).
- **Method References:** `this::mapEntityToResponseDTO` makes the stream map very readable.

---

## 📝 Step-by-Step Explanation

We ask the repository for all students. Since it returns a list of JPA entities (which have internal DB stuff we don't want to expose), we use a Java stream to map each `Student` into a `StudentResponseDTO` and return the resulting list.

---

## 💻 The Final Code

```java
public List<StudentResponseDTO> getAllStudents() {
        log.debug("Fetching all students");

        return studentRepository.findAll()
            .stream()
            .map(this::mapEntityToResponseDTO)   // convert each entity to DTO
            .collect(Collectors.toList());
    }
```

---

## 🚦 Edge Cases Handled

- Empty database (returns an empty list `[]`, not `null`, which is great for frontend rendering).

---

## 🧩 Where does this fit in the app?
Frontend Table -> StudentController -> **StudentService.getAllStudents()** -> StudentRepository -> MySQL.
