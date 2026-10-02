# JAVA-30 — Test Get Student By ID Success

## 🎓 Khushi, what problem does this solve?
**Problem:** Prove we can retrieve a student successfully.

When building a real Java application, we can't just think about the "happy path". Core functionality test.

---

## 🛠️ Why did we use this specific approach?

Mock findById to return an Optional containing a student, assert fields match.

---

## 🧠 Core Java & Spring Concepts Used

- Mocking Optionals

---

## 📝 Step-by-Step Explanation

Verifies DTO mapping works correctly.

---

## 💻 The Final Code

```java

    
    void getStudentById_Success() {
        // ARRANGE: mock the repository to return our sample student
        when(studentRepository.findById(1L)).thenReturn(Optional.of(sampleStudent));

        // ACT
        StudentResponseDTO result = studentService.getStudentById(1L);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Khushi Sharma");
        assertThat(result.getSemester()).isEqualTo(3);
    }
```

---

## 🚦 Edge Cases Handled

None.

---

## 🧩 Where does this fit in the app?
JUnit
