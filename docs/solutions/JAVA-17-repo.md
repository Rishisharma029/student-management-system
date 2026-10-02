# JAVA-17-repo — Search Students Repo

## 🎓 Khushi, what problem does this solve?
**Problem:** We need to find students whose name, email, or roll number contains a specific search term, ignoring uppercase/lowercase.

When building a real Java application, we can't just think about the "happy path". A global search needs to cast a wide net across multiple columns simultaneously.

---

## 🛠️ Why did we use this specific approach?

Using Spring Data JPA's `@Query` annotation to write a custom JPQL query. (You could also use a wildly long derived name like `findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase...` but `@Query` is cleaner!)

---

## 🧠 Core Java & Spring Concepts Used

- **@Query and JPQL:** Writing queries using Java entity names (`Student`) instead of raw SQL table names (`students`).
- **LIKE and Lowercase matching:** `LOWER(s.name) LIKE LOWER(CONCAT('%', :term, '%'))`.

---

## 📝 Step-by-Step Explanation

We explicitly write the JPQL query to check if the lowercase search term exists anywhere inside the lowercase name, email, or roll number.

---

## 💻 The Final Code

```java
// Code not extracted automatically
```

---

## 🚦 Edge Cases Handled

- Empty string (should be handled by service before calling this).

---

## 🧩 Where does this fit in the app?
StudentService.searchStudents() -> **StudentRepository.searchStudents()** -> MySQL.
