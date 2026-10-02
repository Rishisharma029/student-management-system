# JAVA-22 — Handle Duplicate Student

## 🎓 Khushi, what problem does this solve?
**Problem:** When a user tries to create a student with a taken email, we throw `DuplicateStudentException`. We need to return this as an HTTP 409 (Conflict).

When building a real Java application, we can't just think about the "happy path". 409 Conflict is the correct HTTP status code when a request cannot be processed because it conflicts with current data (like uniqueness constraints).

---

## 🛠️ Why did we use this specific approach?

Map `DuplicateStudentException.class` to a 409 response.

---

## 🧠 Core Java & Spring Concepts Used

- **HTTP 409 Conflict:** Standard REST practice for duplicate data errors.

---

## 📝 Step-by-Step Explanation

Builds the `ApiErrorDTO` with status 409 and returns it.

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
Service Layer (Throws) -> **GlobalExceptionHandler (Catches)** -> Frontend (Receives 409 JSON).
