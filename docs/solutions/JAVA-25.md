# JAVA-25 — Handle Validation Errors

## 🎓 Khushi, what problem does this solve?
**Problem:** When a DTO fails `@Valid` checks (e.g., invalid email format, blank name), Spring throws `MethodArgumentNotValidException`. It is very verbose.

When building a real Java application, we can't just think about the "happy path". The frontend needs to know exactly *which* fields failed so it can highlight them in red (e.g., `{"email": "Must be valid format"}`).

---

## 🛠️ Why did we use this specific approach?

We catch `MethodArgumentNotValidException`, iterate over its `FieldErrors`, and build a map of `fieldName -> errorMessage`.

---

## 🧠 Core Java & Spring Concepts Used

- **Jakarta Validation:** `@NotBlank`, `@Email` in DTOs.
- **FieldErrors:** Extracting human-readable messages from Spring's validation binder.

---

## 📝 Step-by-Step Explanation

We loop through all validation errors, put them in a `Map<String, String>`, and attach that map to our `ApiErrorDTO` under the `validationErrors` field with an HTTP 400 Bad Request status.

---

## 💻 The Final Code

```java
// Code not extracted automatically
```

---

## 🚦 Edge Cases Handled

- Multiple fields failing simultaneously (all are collected and returned).

---

## 🧩 Where does this fit in the app?
Controller (@Valid fails) -> **GlobalExceptionHandler (Catches)** -> Frontend Form (Shows red errors).
