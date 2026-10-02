# JAVA-20 — Handle Subject Not Found

## 🎓 Khushi, what problem does this solve?
**Problem:** When `SubjectNotFoundException` is thrown, we need a 404 Not Found response.

When building a real Java application, we can't just think about the "happy path". Standardized error API keeps the frontend from crashing on unhandled errors.

---

## 🛠️ Why did we use this specific approach?

@ExceptionHandler mapping.

---

## 🧠 Core Java & Spring Concepts Used

- Exception Mapping

---

## 📝 Step-by-Step Explanation

Builds a 404 ApiErrorDTO and returns it.

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
GlobalExceptionHandler
