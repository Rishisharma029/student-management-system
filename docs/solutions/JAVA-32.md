# JAVA-32 — Test Get All Students

## 🎓 Khushi, what problem does this solve?
**Problem:** Prove fetching all students works.

When building a real Java application, we can't just think about the "happy path". Core list functionality.

---

## 🛠️ Why did we use this specific approach?

Mock findAll to return a List of 2 students. Verify the service returns a List of 2 DTOs.

---

## 🧠 Core Java & Spring Concepts Used

- Collection Assertions

---

## 📝 Step-by-Step Explanation

Verifies the stream map logic maps all items.

---

## 💻 The Final Code

```java

    
    void getAllStudents_Success() {
        // ARRANGE: mock a list with one student
        when(studentRepository.findAll()).thenReturn(List.of(sampleStudent));

        // ACT
        List<StudentResponseDTO> result = studentService.getAllStudents();

        // ASSERT
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Khushi Sharma");
    }
```

---

## 🚦 Edge Cases Handled

None.

---

## 🧩 Where does this fit in the app?
JUnit
