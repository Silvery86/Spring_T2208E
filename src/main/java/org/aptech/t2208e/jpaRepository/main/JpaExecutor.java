package org.aptech.t2208e.jpaRepository.main;

import org.aptech.t2208e.dto.EmployeeDto;
import org.aptech.t2208e.dto.StudentDto;
import org.aptech.t2208e.entity.Student;

import java.util.List;
import java.util.Optional;

public interface JpaExecutor <T> {
    List<T> findAll();
    Optional<T> findById(Number id);
    List<T> searchByFirstName(String firstName);
    Optional<T> createStudent(StudentDto studentDto);
}
