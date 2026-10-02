# JAVA-37 — Get Students By Semester

## 🎓 Khushi, what problem does this solve?
**Problem:** Filter students by semester.

When building a real Java application, we can't just think about the "happy path". API endpoint requirement.

---

## 🛠️ Why did we use this specific approach?

Call repo, stream, map, collect.

---

## 🧠 Core Java & Spring Concepts Used

- Stream mapping

---

## 📝 Step-by-Step Explanation

Simple delegation to the repository layer.

---

## 💻 The Final Code

```java
public List<StudentResponseDTO> getStudentsBySemester(Integer semester) {
        return studentRepository.findBySemester(semester)
            .stream()
            .map(this::mapEntityToResponseDTO)
            .collect(Collectors.toList());
    }
```

---

## 🚦 Edge Cases Handled

None.

---

## 🧩 Where does this fit in the app?
StudentService -> Repository
