# 🚀 Task Progress Tracker

Update this file as you complete tasks by changing `⬜` to `✅`.

| Task | File | Method | Status |
|------|------|--------|--------|
| **JAVA-01** | `StudentService.java` | `createStudent(StudentRequestDTO requestDTO)` | ⬜ |
| **JAVA-02** | `StudentService.java` | `getAllStudents()` | ⬜ |
| **JAVA-03** | `StudentService.java` | `getStudentById(Long id)` | ⬜ |
| **JAVA-04** | `StudentService.java` | `updateStudent(Long id, StudentRequestDTO requestDTO)` | ⬜ |
| **JAVA-05** | `StudentService.java` | `deleteStudent(Long id)` | ⬜ |
| **JAVA-06** | `StudentService.java` | `searchStudents(String searchTerm)` | ⬜ |
| **JAVA-09** | `StudentRepository.java` | `findByEmail(String email)` | ⬜ |
| **JAVA-10** | `StudentRepository.java` | `findByRollNumber(String rollNumber)` | ⬜ |
| **JAVA-11** | `StudentRepository.java` | `findByCourse(String course)` | ⬜ |
| **JAVA-12** | `StudentRepository.java` | `findBySemester(Integer semester)` | ⬜ |
| **JAVA-13** | `StudentRepository.java` | `existsByEmail(String email)` | ⬜ |
| **JAVA-14** | `StudentRepository.java` | `existsByRollNumber(String rollNumber)` | ⬜ |
| **JAVA-15** | `StudentRepository.java` | `existsByEmailAndIdNot(String email, Long id)` | ⬜ |
| **JAVA-16** | `StudentRepository.java` | `existsByRollNumberAndIdNot(String rollNumber, Long id)` | ⬜ |
| **JAVA-17-repo** | `StudentRepository.java` | `searchStudents(@Param("term") String term)` | ⬜ |
| **JAVA-18-repo** | `StudentRepository.java` | `findTop5ByOrderByCreatedAtDesc()` | ⬜ |
| **JAVA-17-service** | `AttendanceService.java` | `markAttendance(AttendanceRequestDTO requestDTO)` | ⬜ |
| **JAVA-18-service** | `AttendanceService.java` | `getAttendanceSummary(Long studentId, Long subjectId)` | ⬜ |
| **JAVA-19** | `GlobalExceptionHandler.java` | `handleStudentNotFoundException()` | ⬜ |
| **JAVA-20** | `GlobalExceptionHandler.java` | `handleSubjectNotFoundException()` | ⬜ |
| **JAVA-21** | `GlobalExceptionHandler.java` | `handleAttendanceNotFoundException()` | ⬜ |
| **JAVA-22** | `GlobalExceptionHandler.java` | `handleDuplicateStudentException()` | ⬜ |
| **JAVA-23** | `GlobalExceptionHandler.java` | `handleDuplicateSubjectException()` | ⬜ |
| **JAVA-24** | `GlobalExceptionHandler.java` | `handleDuplicateAttendanceException()` | ⬜ |
| **JAVA-25** | `GlobalExceptionHandler.java` | `handleMethodArgumentNotValidException()` | ⬜ |
| **JAVA-26** | `GlobalExceptionHandler.java` | `handleGlobalException()` | ⬜ |
| **JAVA-27** | `StudentServiceTest.java` | `testCreateStudent_Success()` | ⬜ |
| **JAVA-28** | `StudentServiceTest.java` | `testCreateStudent_DuplicateEmail()` | ⬜ |
| **JAVA-29** | `StudentServiceTest.java` | `testCreateStudent_DuplicateRollNumber()` | ⬜ |
| **JAVA-30** | `StudentServiceTest.java` | `testGetStudentById_Success()` | ⬜ |
| **JAVA-31** | `StudentServiceTest.java` | `testGetStudentById_NotFound()` | ⬜ |
| **JAVA-32** | `StudentServiceTest.java` | `testGetAllStudents()` | ⬜ |
| **JAVA-33** | `StudentServiceTest.java` | `testUpdateStudent_Success()` | ⬜ |
| **JAVA-34** | `StudentServiceTest.java` | `testDeleteStudent_Success()` | ⬜ |
| **JAVA-35** | `StudentServiceTest.java` | `testDeleteStudent_NotFound()` | ⬜ |
| **JAVA-36** | `StudentService.java` | `getStudentsByCourse(String course)` | ⬜ |
| **JAVA-37** | `StudentService.java` | `getStudentsBySemester(Integer semester)` | ⬜ |
| **JAVA-38** | `StudentService.java` | `getRecentStudents()` | ⬜ |
