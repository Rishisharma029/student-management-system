# JAVA-38 — Get Recent Students

## 🎓 Khushi, what problem does this solve?
**Problem:** Fetch recent students for dashboard.

When building a real Java application, we can't just think about the "happy path". Dashboard API requirement.

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
public List<StudentResponseDTO> getRecentStudents() {
        return studentRepository.findTop5ByOrderByCreatedAtDesc()
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
