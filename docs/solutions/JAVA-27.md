# JAVA-27 — Test Create Student Success

## 🎓 Khushi, what problem does this solve?
**Problem:** We need an automated unit test to prove that `createStudent` works correctly when valid data is provided.

When building a real Java application, we can't just think about the "happy path". Tests prevent future changes from accidentally breaking core functionality (regression).

---

## 🛠️ Why did we use this specific approach?

Use JUnit 5 and Mockito. We mock the repository to pretend the email/roll are available, and pretend it successfully saves the entity.

---

## 🧠 Core Java & Spring Concepts Used

- **Unit Testing (JUnit 5):** Testing one layer in isolation.
- **Mocking (Mockito):** Faking the database layer (`studentRepository`) so we only test the `StudentService` logic.
- **Given/When/Then:** The standard structure for writing clean tests.

---

## 📝 Step-by-Step Explanation

1. GIVEN: Set up the mock repository to return false for existence checks, and return a saved student on `.save()`. 2. WHEN: Call `service.createStudent()`. 3. THEN: Assert the returned DTO matches our mock data, and verify `save()` was actually called exactly once.

---

## 💻 The Final Code

```java

    
    void createStudent_Success() {
        // ARRANGE: tell the mock repository what to return
        when(studentRepository.existsByEmail(anyString())).thenReturn(false);
        when(studentRepository.existsByRollNumber(anyString())).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(sampleStudent);

        // ACT: call the service method
        StudentResponseDTO result = studentService.createStudent(sampleRequestDTO);

        // ASSERT: verify the result is correct
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Khushi Sharma");
        assertThat(result.getEmail()).isEqualTo("khushi.sharma@college.com");
        assertThat(result.getRollNumber()).isEqualTo("CS2024001");

        // Verify save() was called exactly once
        verify(studentRepository, times(1)).save(any(Student.class));
    }
```

---

## 🚦 Edge Cases Handled

None (this tests the happy path).

---

## 🧩 Where does this fit in the app?
JUnit -> **StudentServiceTest** -> StudentService
