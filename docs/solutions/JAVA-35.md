# JAVA-35 — Test Delete Student Not Found

## 🎓 Khushi, what problem does this solve?
**Problem:** Prove delete fails if student missing.

When building a real Java application, we can't just think about the "happy path". Ensures we don't delete blindly.

---

## 🛠️ Why did we use this specific approach?

Mock findById to return Optional.empty(). Assert exception, verify deleteById is never called.

---

## 🧠 Core Java & Spring Concepts Used

- Mockito.verify(..., never())

---

## 📝 Step-by-Step Explanation

Validates early exit logic.

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
JUnit
