# JAVA-19 — Handle Student Not Found

## 🎓 Khushi, what problem does this solve?
**Problem:** When a `StudentNotFoundException` is thrown anywhere in the application, the user receives an ugly Java stack trace and a 500 error.

When building a real Java application, we can't just think about the "happy path". We need to catch this specific exception globally and convert it into a clean, standardized JSON response with an HTTP 404 (Not Found) status code.

---

## 🛠️ Why did we use this specific approach?

We use `@ExceptionHandler(StudentNotFoundException.class)`.

---

## 🧠 Core Java & Spring Concepts Used

- **@RestControllerAdvice:** A global interceptor for exceptions.
- **@ExceptionHandler:** Maps a specific Java exception to a specific method.
- **HTTP Status Codes:** 404 means the requested resource does not exist.

---

## 📝 Step-by-Step Explanation

When `StudentNotFoundException` is thrown, Spring stops normal execution, routes to this method, builds an `ApiErrorDTO` with the error message and a 404 status, and returns it to the client.

---

## 💻 The Final Code

```java
// Code not extracted automatically
```

---

## 🚦 Edge Cases Handled

None. Simple mapping.

---

## 🧩 Where does this fit in the app?
Service Layer (Throws) -> **GlobalExceptionHandler (Catches)** -> Frontend (Receives 404 JSON).
