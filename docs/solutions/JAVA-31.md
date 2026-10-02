# JAVA-31 — Test Get Student By ID Not Found

## 🎓 Khushi, what problem does this solve?
**Problem:** Prove fetching a non-existent student throws an error.

When building a real Java application, we can't just think about the "happy path". Ensures 404 logic triggers.

---

## 🛠️ Why did we use this specific approach?

Mock findById to return Optional.empty(). AssertThrows StudentNotFoundException.

---

## 🧠 Core Java & Spring Concepts Used

- Testing Exception paths

---

## 📝 Step-by-Step Explanation

Validates the `.orElseThrow()` logic.

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
