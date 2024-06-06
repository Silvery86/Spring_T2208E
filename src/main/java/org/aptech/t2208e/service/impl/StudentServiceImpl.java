package org.aptech.t2208e.service.impl;

import org.aptech.t2208e.dto.StudentDto;
import org.aptech.t2208e.entity.Student;
import org.aptech.t2208e.mapper.StudentMapper;
import org.aptech.t2208e.mapper.impl.StudentMapperImpl;
import org.aptech.t2208e.repository.StudentRepository;
import org.aptech.t2208e.repository.StudentRepositoryReflect;
import org.aptech.t2208e.repository.impl.StudentRepositoryImpl;
import org.aptech.t2208e.service.StudentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentServiceImpl  implements StudentService {

    // create instance of StudentRepositoryImpl --> not work
    StudentRepository studentRepository = new StudentRepositoryImpl();
    StudentMapper studentMapper = new StudentMapperImpl();
    // StudentService >>> StudentRepository
    // instead of create instance -> create instance somewhere , inject to class depended
    StudentRepositoryReflect studentRepository1 = new StudentRepositoryReflect(Student.class);

    @Override
    public List<StudentDto> findAll() {
        List<Student> students = studentRepository1.findAll();
//        // mapping list entity -> dto
//        List<StudentDto> studentDtos = new ArrayList<>();
//        for(Student s : students){
//            StudentDto dto  = studentMapper.entityToDto(s);
//            studentDtos.add(dto);
//        }
//        return studentDtos;
        // lambda function
        return students.stream()
                .map(studentMapper::entityToDto)
                .collect(Collectors.toList());
    }


    public static void main(String[] args) {
        List<String> strs = Arrays.asList("sfdsfd","sfsfsd","sgsdgdsg");
//        for(String s : strs){
//            System.err.println(s);
//        }
        // -> lambda
        strs.forEach(System.err::println);
        List<Student> students = new ArrayList<>();
        // get all name of student -> to list <String>

        List<String> strName = new ArrayList<>();
        for (Student s : students){
            strName.add(s.getFirstName());
        }
        List<String> strName1 = students.stream().map(Student::getFirstName).collect(Collectors.toList());
    }

    @Override
    public StudentDto getById(Long id) {
        Optional<Student> optionalStudent = studentRepository1.findById(id);
        return optionalStudent.map(studentMapper::entityToDto).orElse(null);
    }

    @Override
    public List<StudentDto> searchByFirstName(String firstName) {
        List<Student> students = studentRepository1.searchByFirstName(firstName);
        return students.stream()
                .map(studentMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StudentDto> createStudent(StudentDto studentDto) {
        Optional<Student> optionalStudent = studentRepository1.createStudent(studentDto);
        return optionalStudent.map(studentMapper::entityToDto);
    }




}
