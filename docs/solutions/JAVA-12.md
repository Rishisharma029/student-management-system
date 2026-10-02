# JAVA-12 — Find by Semester

## 🎓 Khushi, what problem does this solve?
**Problem:** We need a list of all students currently in a specific semester.

When building a real Java application, we can't just think about the "happy path". Used for filtering the student list by semester.

---

## 🛠️ Why did we use this specific approach?

Derived query: `List<Student> findBySemester(Integer semester);`.

---

## 🧠 Core Java & Spring Concepts Used

- **Derived Query Methods.**

---

## 📝 Step-by-Step Explanation

Spring generates `SELECT * FROM students WHERE semester = ?`.

---

## 💻 The Final Code

```java

    
    List<Student> findBySemester(Integer semester);
```

---

## 🚦 Edge Cases Handled

- No students in that semester (returns empty list).

---

## 🧩 Where does this fit in the app?
StudentService -> **StudentRepository.findBySemester()** -> MySQL.
