package com.example.studentmanagement.hidden;

import com.example.studentmanagement.dto.AttendanceRequestDTO;
import com.example.studentmanagement.dto.AttendanceSummaryDTO;
import com.example.studentmanagement.dto.StudentRequestDTO;
import com.example.studentmanagement.dto.SubjectRequestDTO;
import com.example.studentmanagement.exception.DuplicateAttendanceException;
import com.example.studentmanagement.model.Attendance.AttendanceStatus;
import com.example.studentmanagement.service.AttendanceService;
import com.example.studentmanagement.service.StudentService;
import com.example.studentmanagement.service.SubjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class HiddenAttendanceServiceTest {

    @Autowired
    private AttendanceService attendanceService;
    
    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectService subjectService;

    @Test
    public void testDuplicateAttendanceOnSameDate() {
        Long studentId = studentService.createStudent(StudentRequestDTO.builder().name("A").email("a@b.c").rollNumber("A1").course("C").semester(1).build()).getId();
        Long subjectId = subjectService.createSubject(SubjectRequestDTO.builder().name("S").code("S1").build()).getId();

        AttendanceRequestDTO req = AttendanceRequestDTO.builder()
                .studentId(studentId)
                .subjectId(subjectId)
                .attendanceDate(LocalDate.now())
                .status(AttendanceStatus.PRESENT)
                .build();
        
        attendanceService.markAttendance(req);

        // Try to mark again on the same day
        assertThatThrownBy(() -> attendanceService.markAttendance(req))
                .isInstanceOf(DuplicateAttendanceException.class);
    }
}
