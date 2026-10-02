# JAVA-06 — Search Students

## 🎓 Khushi, what problem does this solve?
**Problem:** Users need a way to search for students using a single text input that matches against their name, email, or roll number.

When building a real Java application, we can't just think about the "happy path". When the database grows to hundreds of students, scrolling through a table is impossible. A global search feature is required.

---

## 🛠️ Why did we use this specific approach?

We check if the search term is empty; if so, we just return all students. If there is a term, we pass it to a custom repository method `searchStudents(term)` that handles the complex SQL `LIKE` queries.

---

## 🧠 Core Java & Spring Concepts Used

- **Early Return / Guard Clauses:** If `searchTerm` is blank, return early. This saves an unnecessary complex DB query.

---

## 📝 Step-by-Step Explanation

We validate the search term. If valid, we trim the whitespace, pass it to the repository, stream the resulting entities, map them to DTOs, and return the list.

---

## 💻 The Final Code

```java
public List<StudentResponseDTO> searchStudents(String searchTerm) {
        log.debug("Searching students with term: '{}'", searchTerm);

        if (searchTerm == null || searchTerm.isBlank()) {
            return getAllStudents();
        }

        return studentRepository.searchStudents(searchTerm.trim())
            .stream()
            .map(this::mapEntityToResponseDTO)
            .collect(Collectors.toList());
    }
```

---

## 🚦 Edge Cases Handled

- Null or blank search term (returns all students).
- Term that matches nothing (returns empty list `[]`).

---

## 🧩 Where does this fit in the app?
Frontend Search Bar -> StudentController -> **StudentService.searchStudents()** -> StudentRepository -> MySQL.
