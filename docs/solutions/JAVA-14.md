# JAVA-14 — Exists by Roll Number

## 🎓 Khushi, what problem does this solve?
**Problem:** We need a highly efficient way to check if a roll number is already taken.

When building a real Java application, we can't just think about the "happy path". Fast validation during student creation.

---

## 🛠️ Why did we use this specific approach?

Derived query: `boolean existsByRollNumber(String rollNumber);`.

---

## 🧠 Core Java & Spring Concepts Used

- **Exists Projections.**

---

## 📝 Step-by-Step Explanation

Returns true if the roll number is in the database. Very fast SQL execution.

---

## 💻 The Final Code

```java

    
    boolean existsByRollNumber(String rollNumber);
```

---

## 🚦 Edge Cases Handled

None.

---

## 🧩 Where does this fit in the app?
StudentService.createStudent() -> **StudentRepository.existsByRollNumber()** -> MySQL.
