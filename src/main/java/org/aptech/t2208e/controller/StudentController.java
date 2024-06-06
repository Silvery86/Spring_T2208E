package org.aptech.t2208e.controller;

import org.aptech.t2208e.dto.StudentDto;
import org.aptech.t2208e.service.StudentService;
import org.aptech.t2208e.service.impl.StudentServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class StudentController {
    StudentService studentService = new StudentServiceImpl();
    // api/v1/student/1  .. and httpMethod  = GET
    @GetMapping(value = "/student/{studentId}")
    public StudentDto get(@PathVariable Long studentId){
        return studentService.getById(studentId);
    }

    @GetMapping(value = "/students")
    public List<StudentDto> findall(){
        return studentService.findAll();
    }

    @GetMapping(value = "/student_by_firstname/{studentFirstName}")
    public List<StudentDto> searchByFirstName(@PathVariable String studentFirstName){
        return studentService.searchByFirstName(studentFirstName);
    }

    @PutMapping(value = "student/create")
    public Optional<StudentDto> createStudent(@RequestBody StudentDto studentDto){
        System.out.println("Received studentDto: " + studentDto);
        return studentService.createStudent(studentDto);
    }


}
