# JAVA-18-service — Get Attendance Summary

## 🎓 Khushi, what problem does this solve?
**Problem:** We need to calculate a student's overall attendance percentage for a specific subject.

When building a real Java application, we can't just think about the "happy path". Students and teachers need to see percentages (e.g., "85% attendance") rather than just a raw list of PRESENT/ABSENT rows.

---

## 🛠️ Why did we use this specific approach?

Fetch all attendance records for the student+subject. Count how many total classes there were, count how many were PRESENT. Calculate the percentage: `(present / total) * 100`.

---

## 🧠 Core Java & Spring Concepts Used

- **Data Aggregation in Java:** Using Java logic to count and calculate percentages from raw database rows.
- **Math rounding:** Handling division by zero and rounding to 2 decimal places.

---

## 📝 Step-by-Step Explanation

We get the raw records. If total == 0, percentage is 0.0. Otherwise, we calculate the percentage. We then package all these stats into a nice `AttendanceSummaryDTO` so the frontend has everything it needs in one object.

---

## 💻 The Final Code

```java
public AttendanceSummaryDTO getAttendanceSummary(Long studentId, Long subjectId) {
        // Verify both exist
        Student student = findStudentEntityOrThrow(studentId);
        Subject subject = findSubjectEntityOrThrow(subjectId);

        // Count total classes (all attendance records for this student/subject)
        long totalClasses = attendanceRepository.countByStudentIdAndSubjectId(studentId, subjectId);

        // Count how many times the student was PRESENT
        long presentCount = attendanceRepository.countByStudentIdAndSubjectIdAndStatus(
            studentId, subjectId, AttendanceStatus.PRESENT
        );

        long absentCount = totalClasses - presentCount;

        // Calculate percentage — guard against division by zero
        double percentage = 0.0;
        if (totalClasses > 0) {
            percentage = ((double) presentCount / totalClasses) * 100;
            // Round to 2 decimal places: e.g., 84.666... → 84.67
            percentage = Math.round(percentage * 100.0) / 100.0;
        }

        return AttendanceSummaryDTO.builder()
            .studentId(studentId)
            .studentName(student.getName())
            .studentRollNumber(student.getRollNumber())
            .subjectId(subjectId)
            .subjectName(subject.getName())
            .subjectCode(subject.getCode())
            .totalClasses(totalClasses)
            .presentCount(presentCount)
            .absentCount(absentCount)
            .attendancePercentage(percentage)
            .build();
    }
```

---

## 🚦 Edge Cases Handled

- Zero total classes (handled to avoid division by zero `NaN`).
- Student or Subject doesn't exist (throws exception).

---

## 🧩 Where does this fit in the app?
Frontend Dashboard/Details -> AttendanceController -> **AttendanceService.getAttendanceSummary()** -> Repository.
