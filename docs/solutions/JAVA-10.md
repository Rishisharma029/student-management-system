# JAVA-10 — Find by Roll Number

## 🎓 Khushi, what problem does this solve?
**Problem:** We need to look up a student using their exact roll number.

When building a real Java application, we can't just think about the "happy path". Similar to email, we often need to fetch a student by their institution-assigned roll number.

---

## 🛠️ Why did we use this specific approach?

Derived query: `Optional<Student> findByRollNumber(String rollNumber);`

---

## 🧠 Core Java & Spring Concepts Used

- **Derived Query Methods.**

---

## 📝 Step-by-Step Explanation

Spring Boot generates `SELECT * FROM students WHERE roll_number = ?`.

---

## 💻 The Final Code

```java

    
    Optional<Student> findByRollNumber(String rollNumber);
```

---

## 🚦 Edge Cases Handled

- No student found (returns `Optional.empty()`).

---

## 🧩 Where does this fit in the app?
Service Layer -> **StudentRepository.findByRollNumber()** -> MySQL Database.
