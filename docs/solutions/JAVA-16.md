# JAVA-16 — Exists by Roll Number Excluding ID

## 🎓 Khushi, what problem does this solve?
**Problem:** During an update, we need to check if a roll number belongs to **someone else**.

When building a real Java application, we can't just think about the "happy path". Prevents a student from stealing a roll number, while allowing them to keep their own.

---

## 🛠️ Why did we use this specific approach?

Derived query: `boolean existsByRollNumberAndIdNot(String rollNumber, Long id);`.

---

## 🧠 Core Java & Spring Concepts Used

- **Compound Derived Queries.**

---

## 📝 Step-by-Step Explanation

Generates `SELECT 1 FROM students WHERE roll_number = ? AND id <> ? LIMIT 1`.

---

## 💻 The Final Code

```java
// Code not extracted automatically
```

---

## 🚦 Edge Cases Handled

- Same as email check.

---

## 🧩 Where does this fit in the app?
StudentService.updateStudent() -> **StudentRepository.existsByRollNumberAndIdNot()** -> MySQL.
