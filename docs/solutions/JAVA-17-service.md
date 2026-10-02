# JAVA-17-service — Mark Attendance

## 🎓 Khushi, what problem does this solve?
**Problem:** We need to record whether a student was PRESENT or ABSENT for a specific subject on a specific date.

When building a real Java application, we can't just think about the "happy path". This is the core functionality of the Attendance system. We must also strictly prevent marking attendance twice for the same student/subject/date combination.

---

## 🛠️ Why did we use this specific approach?

Fetch student, fetch subject, check if attendance already exists for that date. If it exists, throw `DuplicateAttendanceException`. If not, build the `Attendance` entity and save it.

---

## 🧠 Core Java & Spring Concepts Used

- **Relational Mapping:** Building an entity (`Attendance`) that links two other entities (`Student` and `Subject`).
- **Business Validation:** Enforcing uniqueness before the database throws a hard constraint violation.

---

## 📝 Step-by-Step Explanation

1. Validate student exists. 2. Validate subject exists. 3. Query repository to ensure no record exists for this student+subject+date. 4. Save and map to response DTO.

---

## 💻 The Final Code

```java
public AttendanceResponseDTO markAttendance(AttendanceRequestDTO requestDTO) {
        log.info("Marking attendance: student={}, subject={}, date={}, status={}",
            requestDTO.getStudentId(),
            requestDTO.getSubjectId(),
            requestDTO.getAttendanceDate(),
            requestDTO.getStatus()
        );

        // Step 1: Verify both student and subject exist
        // These methods throw NotFoundException if not found
        Student student = findStudentEntityOrThrow(requestDTO.getStudentId());
        Subject subject = findSubjectEntityOrThrow(requestDTO.getSubjectId());

        // Step 2: Check for duplicate attendance
        boolean alreadyMarked = attendanceRepository.existsByStudentIdAndSubjectIdAndAttendanceDate(
            requestDTO.getStudentId(),
            requestDTO.getSubjectId(),
            requestDTO.getAttendanceDate()
        );

        if (alreadyMarked) {
            throw new DuplicateAttendanceException(
                "Attendance for student ID " + requestDTO.getStudentId() +
                " in subject ID " + requestDTO.getSubjectId() +
                " on " + requestDTO.getAttendanceDate() + " has already been marked"
            );
        }

        // Step 3: Build the Attendance entity
        Attendance attendance = Attendance.builder()
            .student(student)
            .subject(subject)
            .attendanceDate(requestDTO.getAttendanceDate())
            .status(requestDTO.getStatus())
            .build();

        // Step 4: Save to database
        Attendance saved = attendanceRepository.save(attendance);

        log.info("Attendance marked successfully with ID: {}", saved.getId());
        return mapEntityToResponseDTO(saved);
    }
```

---

## 🚦 Edge Cases Handled

- Invalid student or subject (Throws NotFound).
- Attendance already marked today (Throws Duplicate).

---

## 🧩 Where does this fit in the app?
Frontend Mark Button -> AttendanceController -> **AttendanceService.markAttendance()** -> Repository -> MySQL.
