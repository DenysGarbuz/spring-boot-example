package com.example.demo.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository repository;
    @Mock
    private StudentMapper studentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_return_list_of_studentResponseDto() {
        List<Student> mockStudentsList = List.of(
                new Student("John", "Dou", "john.dou@gmail.com"),
                new Student("John", "Dou", "john.dou@gmail.com")
        );

        when(repository.findAll()).thenReturn(mockStudentsList);
        when(studentMapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "John", "Dou", "john.dou@gmail.com"
                ));

        List<StudentResponseDto> studentResponseDtos = studentService.getAllStudents();

        assertEquals(mockStudentsList.size(), studentResponseDtos.size());
        verify(repository, times(1)).findAll();

    }

    @Test
    public void should_return_student_by_id() {
        Student student = new Student("John", "Dou", "john.dou@gmail.com");
        int studentId = 1;

        when(repository.findById(studentId)).thenReturn(
                Optional.of(student));

        when(studentMapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "John", "Dou", "john.dou@gmail.com"
                ));

        StudentResponseDto responseDto = studentService.getStudentById(studentId);

        assertEquals(student.getName(), responseDto.name());
        assertEquals(student.getEmail(), responseDto.email());
        assertEquals(student.getLastname(), responseDto.lastname());

        verify(repository, times(1)).findById(studentId);

    }

    @Test
    public void should_return_student_by_name() {
        Student student = new Student("John", "Dou", "john.dou@gmail.com");
        String name = "John";

        when(repository.findAllByNameContaining(name)).thenReturn(List.of(student));
        when(studentMapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "John", "Dou", "john.dou@gmail.com"
                ));

        List<StudentResponseDto> dtos = studentService.getStudentByName(name);

        assertEquals(dtos.size(), 1);


        verify(repository, times(1)).findAllByNameContaining(name);

    }

    @Test
    public void should_successfully_create_a_student() {
        StudentDto dto = new StudentDto("John", "Doe", "john@mail.com", 1);

        Student student = new Student();
        student.setName("John");
        student.setEmail("john@mail.com");
        student.setLastname("Doe");
        student.setAge(20);

        Student saveStudent = new Student();
        student.setName("John");
        student.setEmail("john@mail.com");
        student.setLastname("Doe");
        student.setAge(20);
        student.setId(1);


        when(studentMapper.toStudent(dto)).thenReturn(student);
        when(repository.save(student)).thenReturn(saveStudent);
        when(studentMapper.toStudentResponseDto(saveStudent))
                .thenReturn(new StudentResponseDto("John", "Doe", "john@mail.com"));

        StudentResponseDto responseDto = studentService.saveStudent(dto);
        assertEquals(dto.name(), responseDto.name());
        assertEquals(dto.email(), responseDto.email());
        assertEquals(dto.lastname(), responseDto.lastname());

        verify(studentMapper, times(1)).toStudent(dto);
        verify(repository, times(1)).save(student);
        verify(studentMapper, times(1)).toStudentResponseDto(saveStudent);

    }


}