# JAVA-13 — Exists by Email

## 🎓 Khushi, what problem does this solve?
**Problem:** We need a highly efficient way to check if an email is already taken, without fetching the entire Student row.

When building a real Java application, we can't just think about the "happy path". Fetching the whole row just to check existence wastes memory and database bandwidth. We just want a simple true/false.

---

## 🛠️ Why did we use this specific approach?

Derived query: `boolean existsByEmail(String email);`.

---

## 🧠 Core Java & Spring Concepts Used

- **Exists Projections:** `existsBy...` generates an optimized SQL query like `SELECT 1 FROM students WHERE email = ? LIMIT 1`. It is incredibly fast.

---

## 📝 Step-by-Step Explanation

Returns true if the email is in the database, false otherwise. Used during `createStudent` validation.

---

## 💻 The Final Code

```java

    
    boolean existsByEmail(String email);
```

---

## 🚦 Edge Cases Handled

None.

---

## 🧩 Where does this fit in the app?
StudentService.createStudent() -> **StudentRepository.existsByEmail()** -> MySQL.
