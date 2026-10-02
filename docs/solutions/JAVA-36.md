# JAVA-36 — Get Students By Course

## 🎓 Khushi, what problem does this solve?
**Problem:** Filter students by course.

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
public List<StudentResponseDTO> getStudentsByCourse(String course) {
        return studentRepository.findByCourse(course)
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
