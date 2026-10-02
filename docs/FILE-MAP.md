# 🗺️ Quick File Map

Not sure where a file is? Here is your quick navigation cheat sheet.

### StudentService
**Path:** `backend/src/main/java/com/example/studentmanagement/service/StudentService.java`

- **Create Student** → `createStudent(StudentRequestDTO requestDTO)`
- **Get All Students** → `getAllStudents()`
- **Get Student By ID** → `getStudentById(Long id)`
- **Update Student** → `updateStudent(Long id, StudentRequestDTO requestDTO)`
- **Delete Student** → `deleteStudent(Long id)`
- **Search Students** → `searchStudents(String searchTerm)`
- **Get Students By Course** → `getStudentsByCourse(String course)`
- **Get Students By Semester** → `getStudentsBySemester(Integer semester)`
- **Get Recent Students** → `getRecentStudents()`

### StudentRepository
**Path:** `backend/src/main/java/com/example/studentmanagement/repository/StudentRepository.java`

- **Find by Email** → `findByEmail(String email)`
- **Find by Roll Number** → `findByRollNumber(String rollNumber)`
- **Find by Course** → `findByCourse(String course)`
- **Find by Semester** → `findBySemester(Integer semester)`
- **Exists by Email** → `existsByEmail(String email)`
- **Exists by Roll Number** → `existsByRollNumber(String rollNumber)`
- **Exists by Email Excluding ID** → `existsByEmailAndIdNot(String email, Long id)`
- **Exists by Roll Number Excluding ID** → `existsByRollNumberAndIdNot(String rollNumber, Long id)`
- **Search Students Repo Query** → `searchStudents(@Param("term") String term)`
- **Find Recent Students** → `findTop5ByOrderByCreatedAtDesc()`

### AttendanceService
**Path:** `backend/src/main/java/com/example/studentmanagement/service/AttendanceService.java`

- **Mark Attendance** → `markAttendance(AttendanceRequestDTO requestDTO)`
- **Get Attendance Summary** → `getAttendanceSummary(Long studentId, Long subjectId)`

### GlobalExceptionHandler
**Path:** `backend/src/main/java/com/example/studentmanagement/exception/GlobalExceptionHandler.java`

- **Handle Student Not Found** → `handleStudentNotFoundException()`
- **Handle Subject Not Found** → `handleSubjectNotFoundException()`
- **Handle Attendance Not Found** → `handleAttendanceNotFoundException()`
- **Handle Duplicate Student** → `handleDuplicateStudentException()`
- **Handle Duplicate Subject** → `handleDuplicateSubjectException()`
- **Handle Duplicate Attendance** → `handleDuplicateAttendanceException()`
- **Handle Validation Errors** → `handleMethodArgumentNotValidException()`
- **Handle Global Exception** → `handleGlobalException()`

### StudentServiceTest
**Path:** `backend/src/test/java/com/example/studentmanagement/service/StudentServiceTest.java`

- **Test Create Student Success** → `testCreateStudent_Success()`
- **Test Create Duplicate Email** → `testCreateStudent_DuplicateEmail()`
- **Test Create Duplicate Roll Number** → `testCreateStudent_DuplicateRollNumber()`
- **Test Get Student By ID Success** → `testGetStudentById_Success()`
- **Test Get Student By ID Not Found** → `testGetStudentById_NotFound()`
- **Test Get All Students** → `testGetAllStudents()`
- **Test Update Student Success** → `testUpdateStudent_Success()`
- **Test Delete Student Success** → `testDeleteStudent_Success()`
- **Test Delete Student Not Found** → `testDeleteStudent_NotFound()`

