# JAVA-18-repo — Find Recent Students

## 🎓 Khushi, what problem does this solve?
**Problem:** The dashboard needs to display the 5 most recently registered students.

When building a real Java application, we can't just think about the "happy path". We need a way to sort by creation date descending, and limit the result to 5.

---

## 🛠️ Why did we use this specific approach?

Derived query: `List<Student> findTop5ByOrderByCreatedAtDesc();`.

---

## 🧠 Core Java & Spring Concepts Used

- **Top/First Projections:** `findTop5By...` automatically adds `LIMIT 5` to the SQL.
- **OrderBy:** `OrderByCreatedAtDesc` adds `ORDER BY created_at DESC`.

---

## 📝 Step-by-Step Explanation

Spring Boot parses the method name to generate a highly efficient SQL query that sorts and limits the results exactly as needed.

---

## 💻 The Final Code

```java

    
    List<Student> findTop5ByOrderByCreatedAtDesc();
```

---

## 🚦 Edge Cases Handled

- Less than 5 students exist (returns however many exist).

---

## 🧩 Where does this fit in the app?
StudentService.getRecentStudents() -> **StudentRepository.findTop5...()** -> MySQL.
