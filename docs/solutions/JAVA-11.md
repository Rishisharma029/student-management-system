# JAVA-11 — Find by Course

## 🎓 Khushi, what problem does this solve?
**Problem:** We need a list of all students enrolled in a specific course (e.g., "Computer Science").

When building a real Java application, we can't just think about the "happy path". Used for filtering the student list by course.

---

## 🛠️ Why did we use this specific approach?

Derived query: `List<Student> findByCourse(String course);`. Returns a List instead of an Optional because many students can belong to one course.

---

## 🧠 Core Java & Spring Concepts Used

- **Collection Return Types in JPA:** If a query can return multiple rows, you declare it as returning a `List` or `Set`.

---

## 📝 Step-by-Step Explanation

Spring generates `SELECT * FROM students WHERE course = ?`.

---

## 💻 The Final Code

```java

    
    List<Student> findByCourse(String course);
```

---

## 🚦 Edge Cases Handled

- No students in that course (returns empty list, not null).

---

## 🧩 Where does this fit in the app?
StudentService -> **StudentRepository.findByCourse()** -> MySQL.
