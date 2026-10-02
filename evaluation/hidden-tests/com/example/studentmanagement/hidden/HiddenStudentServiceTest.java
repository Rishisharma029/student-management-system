package com.example.studentmanagement.hidden;

import com.example.studentmanagement.dto.StudentRequestDTO;
import com.example.studentmanagement.dto.StudentResponseDTO;
import com.example.studentmanagement.exception.DuplicateStudentException;
import com.example.studentmanagement.exception.StudentNotFoundException;
import com.example.studentmanagement.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class HiddenStudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void testBoundarySemester() {
        StudentRequestDTO req = StudentRequestDTO.builder()
                .name("Test Bound")
                .email("bound@test.com")
                .rollNumber("B100")
                .course("CS")
                .semester(8) // boundary
                .build();
        StudentResponseDTO res = studentService.createStudent(req);
        assertThat(res.getSemester()).isEqualTo(8);
    }

    @Test
    public void testUpdatePreservesOtherFields() {
        StudentRequestDTO req1 = StudentRequestDTO.builder()
                .name("Update Test")
                .email("update@test.com")
                .rollNumber("U100")
                .course("CS")
                .semester(1)
                .build();
        StudentResponseDTO res1 = studentService.createStudent(req1);

        StudentRequestDTO req2 = StudentRequestDTO.builder()
                .name("Update Test Changed")
                .email("update@test.com") // Same email
                .rollNumber("U100") // Same roll
                .course("CS")
                .semester(2)
                .build();
        StudentResponseDTO res2 = studentService.updateStudent(res1.getId(), req2);

        assertThat(res2.getName()).isEqualTo("Update Test Changed");
        assertThat(res2.getSemester()).isEqualTo(2);
    }
}
