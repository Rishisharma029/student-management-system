# JAVA-34 — Test Delete Student Success

## 🎓 Khushi, what problem does this solve?
**Problem:** Prove delete works.

When building a real Java application, we can't just think about the "happy path". Core functionality.

---

## 🛠️ Why did we use this specific approach?

Mock findById to return a student. Verify deleteById is called.

---

## 🧠 Core Java & Spring Concepts Used

- Mockito.verify

---

## 📝 Step-by-Step Explanation

We don't assert a return value (since it's void), we assert that the correct repository method was invoked.

---

## 💻 The Final Code

```java

    
    void deleteStudent_Success() {
        // ARRANGE
        when(studentRepository.findById(1L)).thenReturn(Optional.of(sampleStudent));

        // ACT
        studentService.deleteStudent(1L);

        // ASSERT: verify deleteById was called
        verify(studentRepository, times(1)).deleteById(1L);
    }
```

---

## 🚦 Edge Cases Handled

None.

---

## 🧩 Where does this fit in the app?
JUnit
