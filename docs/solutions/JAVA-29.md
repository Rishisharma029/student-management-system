# JAVA-29 — Test Create Duplicate Roll Number

## 🎓 Khushi, what problem does this solve?
**Problem:** Prove that `createStudent` blocks creation if the roll number exists.

When building a real Java application, we can't just think about the "happy path". Ensures roll number uniqueness is enforced.

---

## 🛠️ Why did we use this specific approach?

Mock `existsByRollNumber` to return `true`. Expect `DuplicateStudentException`.

---

## 🧠 Core Java & Spring Concepts Used

- **Mockito.verify(..., never()):** Ensures the database save operation was aborted.

---

## 📝 Step-by-Step Explanation

Mock the DB to return true for roll number existence, assert the exception, verify save() never happened.

---

## 💻 The Final Code

```java
// Code not extracted automatically
```

---

## 🚦 Edge Cases Handled

None.

---

## 🧩 Where does this fit in the app?
JUnit -> **StudentServiceTest** -> StudentService
