package org.aptech.t2208e.service;

import org.aptech.t2208e.dto.StudentDto;

import java.util.List;

public interface StudentService {
    StudentDto getById(Long id);
    List<StudentDto> searchByFirstName(String firstName);
    List<StudentDto> findAll();
}
