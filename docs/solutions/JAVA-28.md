# JAVA-28 — Test Create Duplicate Email

## 🎓 Khushi, what problem does this solve?
**Problem:** We must prove that `createStudent` correctly blocks creation if the email is already in the database.

When building a real Java application, we can't just think about the "happy path". Ensures our business validation logic actually works.

---

## 🛠️ Why did we use this specific approach?

Mock `existsByEmail` to return `true`. Then assert that calling `createStudent` throws a `DuplicateStudentException`.

---

## 🧠 Core Java & Spring Concepts Used

- **AssertThrows:** A JUnit feature to verify that a specific exception is triggered.

---

## 📝 Step-by-Step Explanation

We force the mock repository to say "Yes, this email exists". We then expect our service to throw an exception. We also verify that `repository.save()` was NEVER called, proving data was protected.

---

## 💻 The Final Code

```java
// Code not extracted automatically
```

---

## 🚦 Edge Cases Handled

None (testing the failure path).

---

## 🧩 Where does this fit in the app?
JUnit -> **StudentServiceTest** -> StudentService
