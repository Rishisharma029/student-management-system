# 📚 API Documentation

Base URL: `http://localhost:8080/api`  
Swagger UI: `http://localhost:8080/swagger-ui.html`

All responses are JSON. All request bodies are JSON.

---

## ✅ Response Conventions

| Status | Meaning |
|--------|----------|
| 200 | Success (GET, PUT) |
| 201 | Created (POST) |
| 204 | Deleted (DELETE) |
| 400 | Validation error |
| 404 | Resource not found |
| 409 | Conflict (duplicate) |
| 500 | Server error |

---

## Error Response Format

Every error returns this structure:
```json
{
  "timestamp": "2024-03-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Student with ID 99 was not found",
  "path": "/api/students/99"
}
```

For validation errors, a `validationErrors` map is also included:
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed. Please check the fields below.",
  "validationErrors": {
    "email": "Email must be a valid email address",
    "semester": "Semester must be at least 1"
  }
}
```

---

## Students API

### POST /api/students
Create a new student.

**Request body:**
```json
{
  "name": "Khushi Sharma",
  "email": "khushi@college.com",
  "phone": "9876543210",
  "rollNumber": "CS2024001",
  "course": "Computer Science",
  "semester": 3
}
```

**Success (201):**
```json
{
  "id": 1,
  "name": "Khushi Sharma",
  "email": "khushi@college.com",
  "phone": "9876543210",
  "rollNumber": "CS2024001",
  "course": "Computer Science",
  "semester": 3,
  "createdAt": "2024-03-15T10:30:00",
  "updatedAt": "2024-03-15T10:30:00"
}
```

---

### GET /api/students
Get all students. Optional query parameters:

| Parameter | Type | Description |
|-----------|------|-------------|
| `search` | string | Search by name, email, or roll number |
| `course` | string | Filter by course |
| `semester` | integer | Filter by semester |

**Examples:**
```
GET /api/students
GET /api/students?search=khushi
GET /api/students?course=Computer+Science
GET /api/students?semester=3
```

---

### GET /api/students/{id}
Get a single student by ID.

**Success (200):** Same as the create response above.

**Error (404):**
```json
{ "status": 404, "message": "Student with ID 99 was not found" }
```

---

### PUT /api/students/{id}
Update a student. Same request body as POST.

---

### DELETE /api/students/{id}
Delete a student. Returns 204 No Content.

---

## Subjects API

### POST /api/subjects
```json
{ "name": "Data Structures", "code": "CS201" }
```

### GET /api/subjects
Returns array of all subjects.

### GET /api/subjects/{id} | PUT /api/subjects/{id} | DELETE /api/subjects/{id}
Standard CRUD operations.

---

## Attendance API

### POST /api/attendance
Mark attendance for a student.

**Request body:**
```json
{
  "studentId": 1,
  "subjectId": 2,
  "attendanceDate": "2024-03-15",
  "status": "PRESENT"
}
```

**Status values:** `PRESENT` | `ABSENT`

---

### GET /api/attendance/student/{studentId}
Get all attendance records for a student.

---

### GET /api/attendance/subject/{subjectId}
Get all attendance records for a subject.

---

### GET /api/attendance/summary/{studentId}/{subjectId}
Get attendance percentage.

**Response:**
```json
{
  "studentId": 1,
  "studentName": "Khushi Sharma",
  "subjectId": 2,
  "subjectName": "Data Structures",
  "totalClasses": 50,
  "presentCount": 42,
  "absentCount": 8,
  "attendancePercentage": 84.0
}
```

---

### PUT /api/attendance/{id}?status=ABSENT
Update attendance status.

### DELETE /api/attendance/{id}
Delete an attendance record.

---

## Dashboard API

### GET /api/dashboard/stats
**Response:**
```json
{
  "totalStudents": 8,
  "totalSubjects": 5,
  "totalAttendanceRecords": 50,
  "overallAttendancePercentage": 84.0,
  "recentStudents": [...]
}
```
