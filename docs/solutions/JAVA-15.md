# JAVA-15 — Exists by Email Excluding ID

## 🎓 Khushi, what problem does this solve?
**Problem:** During a student update, we need to check if the new email they want is taken by **someone else**.

When building a real Java application, we can't just think about the "happy path". If Khushi (ID 1) updates her phone number but keeps her email `khushi@test.com`, a simple `existsByEmail` would return `true` (because her own record has it!) and falsely block the update.

---

## 🛠️ Why did we use this specific approach?

Derived query: `boolean existsByEmailAndIdNot(String email, Long id);`.

---

## 🧠 Core Java & Spring Concepts Used

- **Compound Derived Queries:** Combining two conditions (`Email` AND `IdNot`). The `Not` keyword creates a `<>` (not equal) SQL condition.

---

## 📝 Step-by-Step Explanation

Generates `SELECT 1 FROM students WHERE email = ? AND id <> ? LIMIT 1`. This safely ignores the student currently being updated.

---

## 💻 The Final Code

```java
// Code not extracted automatically
```

---

## 🚦 Edge Cases Handled

- Email belongs to current student (returns false - allowed).
- Email belongs to another student (returns true - blocked).

---

## 🧩 Where does this fit in the app?
StudentService.updateStudent() -> **StudentRepository.existsByEmailAndIdNot()** -> MySQL.
