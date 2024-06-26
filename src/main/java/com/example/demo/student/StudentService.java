package com.example.demo.student;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    public StudentService(StudentMapper studentMapper, StudentRepository studentRepository) {
        this.studentMapper = studentMapper;
        this.studentRepository = studentRepository;
    }

    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;

    public StudentResponseDto saveStudent(StudentDto dto) {
        var student = studentMapper.toStudent(dto);
        var savedStudent = studentRepository.save(student);
        return studentMapper.toStudentResponseDto(savedStudent);
    }

    public List<StudentResponseDto> getAllStudents() {
        var listOfStudents = studentRepository.findAll();
        return listOfStudents
                .stream()
                .map(studentMapper::toStudentResponseDto)
                .collect(Collectors.toList());
    }

    public StudentResponseDto getStudentById(Integer id) {
        var student = studentRepository
                .findById(id)
                .orElse(new Student());

        return studentMapper.toStudentResponseDto(student);

    }

    public List<StudentResponseDto> getStudentByName(String name) {
        var listOfStudents = studentRepository.findAllByNameContaining(name);
        return listOfStudents
                .stream()
                .map(studentMapper::toStudentResponseDto)
                .collect(Collectors.toList());
    }


    public void deleteById(Integer studentId) {
        this.studentRepository.deleteById(studentId);
    }
}
