package com.example.demo.student;


import com.example.demo.school.School;
import org.springframework.stereotype.Service;

@Service
public class StudentMapper {


    public Student toStudent(StudentDto studentDto) {
        if (studentDto == null) {
            throw new NullPointerException();
        }

        var student = new Student();
        student.setName(studentDto.name());
        student.setEmail(studentDto.email());
        student.setLastname(studentDto.lastname());

        var school = new School();
        school.setId(studentDto.schoolId());

        student.setSchool(school);
        return student;
    }

    public StudentResponseDto toStudentResponseDto(Student student) {
        return new StudentResponseDto(
                student.getName(),
                student.getLastname(),
                student.getEmail()
        );
    }
}
