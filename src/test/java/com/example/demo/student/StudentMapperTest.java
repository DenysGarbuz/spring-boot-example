package com.example.demo.student;

import com.example.demo.school.School;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {


    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StudentMapper();
    }

    @Test
    public void shouldMapStudentDtoToStudent(){
        StudentDto dto = new StudentDto("John", "Doe", "john@mail.com", 1);
        Student student = mapper.toStudent(dto);

        assertEquals(dto.name(), student.getName());
        assertEquals(dto.email(), student.getEmail());
        assertEquals(dto.lastname(), student.getLastname());
        assertNotNull(student.getSchool());
        assertEquals(dto.schoolId(), student.getSchool().getId());

    }

    @Test
    public void shouldMapStudentToStudentDto(){
        Student student = new Student();
        student.setName("John");
        student.setEmail("john@mail.com");
        student.setLastname("Doe");
        student.setAge(20);

        StudentResponseDto dto = mapper.toStudentResponseDto(student);

        assertEquals(dto.name(), student.getName());
        assertEquals(dto.email(), student.getEmail());
        assertEquals(dto.lastname(), student.getLastname());


    }
    @Test
    public void should_map_studentDto_to_student_when_studentDto_is_null(){
        assertThrows(NullPointerException.class, () -> mapper.toStudent(null));

    }


}