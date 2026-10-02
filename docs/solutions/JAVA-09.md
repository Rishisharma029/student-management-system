# JAVA-09 — Find by Email

## 🎓 Khushi, what problem does this solve?
**Problem:** We need to look up a student using their exact email address.

When building a real Java application, we can't just think about the "happy path". Instead of writing manual SQL `SELECT * FROM students WHERE email = ?`, we need Spring Data JPA to do it automatically.

---

## 🛠️ Why did we use this specific approach?

We declare `Optional<Student> findByEmail(String email);`. Spring Data JPA parses the method name and automatically generates the exact SQL required.

---

## 🧠 Core Java & Spring Concepts Used

- **Spring Data JPA Derived Query Methods:** Magic methods! Spring parses "findBy" + "Email" and knows exactly what SQL to write under the hood.
- **Optional:** Returns Optional because the email might not exist.

---

## 📝 Step-by-Step Explanation

By simply declaring the interface signature, Spring Boot provides the implementation at runtime. We don't have to write a single line of SQL.

---

## 💻 The Final Code

```java

    
    Optional<Student> findByEmail(String email);
```

---

## 🚦 Edge Cases Handled

- No student found (returns `Optional.empty()`).

---

## 🧩 Where does this fit in the app?
Service Layer -> **StudentRepository.findByEmail()** -> MySQL Database.
