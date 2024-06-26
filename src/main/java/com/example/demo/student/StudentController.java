package com.example.demo.student;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class StudentController {


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    private final StudentService studentService;


    @GetMapping("/students")
    public List<StudentResponseDto> sayHello() {
        return this.studentService.getAllStudents();
    }

    @PostMapping("/students")
    public StudentResponseDto sayHelloTo(
            @RequestBody @Valid StudentDto dto
    ) {
        return this.studentService.saveStudent(dto);
    }

    @GetMapping("/students/{student-id}")
    public StudentResponseDto getStudent(@PathVariable("student-id") Integer studentId) {
        return this.studentService.getStudentById(studentId);
    }

    @GetMapping("/students/search/{student-name}")
    public List<StudentResponseDto> findStudentByName(@PathVariable("student-name") String studentName) {
        return this.studentService.getStudentByName(studentName);
    }

    @DeleteMapping("/students/{student-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(@PathVariable("student-id") Integer studentId) {
        this.studentService.deleteById(studentId);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ){
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors()
                .forEach(er -> {
                    var fieldName = ((FieldError)er).getField();
                    var errorMessage = er.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
