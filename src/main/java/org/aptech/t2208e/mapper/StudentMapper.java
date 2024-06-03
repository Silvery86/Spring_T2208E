package org.aptech.t2208e.mapper;

import org.aptech.t2208e.dto.StudentDto;
import org.aptech.t2208e.entity.Student;

public interface StudentMapper {
    Student dtoToEntity(StudentDto dto);
    StudentDto entityToDto(Student entity);
}
