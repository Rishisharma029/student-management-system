# JAVA-26 — Handle Global Exception

## 🎓 Khushi, what problem does this solve?
**Problem:** What happens if a NullPointerException or Database connection failure happens? The app crashes and sends a stack trace.

When building a real Java application, we can't just think about the "happy path". We must NEVER leak raw Java stack traces to the frontend for security and UX reasons.

---

## 🛠️ Why did we use this specific approach?

A catch-all `@ExceptionHandler(Exception.class)` that catches absolutely everything that wasn't caught by the specific handlers above.

---

## 🧠 Core Java & Spring Concepts Used

- **Catch-All Exception Handler:** The final safety net in the exception hierarchy.

---

## 📝 Step-by-Step Explanation

Logs the scary error on the server side so developers can fix it, but returns a generic "An unexpected error occurred" JSON response with HTTP 500 to the user.

---

## 💻 The Final Code

```java
// Code not extracted automatically
```

---

## 🚦 Edge Cases Handled

Catches literally any unhandled `RuntimeException`.

---

## 🧩 Where does this fit in the app?
Anywhere in App (Crashes) -> **GlobalExceptionHandler (Catches Exception.class)** -> Frontend (Receives 500).
