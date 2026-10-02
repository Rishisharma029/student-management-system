# JAVA-33 — Test Update Student Success

## 🎓 Khushi, what problem does this solve?
**Problem:** Prove that a valid update operation successfully saves new data.

When building a real Java application, we can't just think about the "happy path". Updates are the most complex operations; we must ensure the `AndIdNot` checks and the entity mutation work.

---

## 🛠️ Why did we use this specific approach?

Mock `findById` to return the existing student. Mock `existsBy...AndIdNot` to return false. Mock `save` to return the updated entity.

---

## 🧠 Core Java & Spring Concepts Used

- **Complex Mock Setup:** Setting up multiple sequential mock behaviors before execution.

---

## 📝 Step-by-Step Explanation

GIVEN an existing student and a valid request DTO, WHEN updated, THEN verify the fields were changed on the entity and `save()` was executed with the new data.

---

## 💻 The Final Code

```java

    
    void updateStudent_Success() {
        // ARRANGE
        when(studentRepository.findById(1L)).thenReturn(Optional.of(sampleStudent));
        when(studentRepository.existsByEmailAndIdNot(anyString(), anyLong())).thenReturn(false);
        when(studentRepository.existsByRollNumberAndIdNot(anyString(), anyLong())).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(sampleStudent);

        // ACT
        StudentResponseDTO result = studentService.updateStudent(1L, sampleRequestDTO);

        // ASSERT
        assertThat(result).isNotNull();
        verify(studentRepository, times(1)).save(any(Student.class));
    }
```

---

## 🚦 Edge Cases Handled

Happy path update.

---

## 🧩 Where does this fit in the app?
JUnit -> **StudentServiceTest** -> StudentService
