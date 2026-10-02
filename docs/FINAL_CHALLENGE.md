# Final Challenge: Course Enrollment Module

## Objective
Build a robust enrollment system connecting students and courses, managing capacity, and tracking grades.

## Requirements
1. **Enrollment Entity**: Create an `Enrollment` entity that maps which student is enrolled in which course, along with an enrollment date and an optional `grade` field.
2. **Course Capacity**: Update the `Course` entity to have a `maxCapacity` field.
3. **Business Rules**:
   - A student cannot enroll in the same course twice.
   - A course cannot exceed its `maxCapacity`. If it's full, the enrollment must be rejected with a custom `CourseFullException`.
4. **Grading**: Implement an API for instructors to assign a grade to an enrolled student.

## Required APIs
- `POST /api/v1/enrollments` - Enroll a student in a course (body: studentId, courseId).
- `GET /api/v1/courses/{courseId}/students` - View all students enrolled in a specific course.
- `PUT /api/v1/enrollments/{enrollmentId}/grade` - Assign or update a grade.
- `GET /api/v1/students/{studentId}/transcript` - View a student's grades and total credits.

*Note: No implementation code is provided. You must design and build this module independently using the patterns learned.*
