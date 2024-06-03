package org.aptech.t2208e.mapper.impl;

import org.aptech.t2208e.dto.StudentDto;
import org.aptech.t2208e.entity.Student;
import org.aptech.t2208e.mapper.StudentMapper;
import org.springframework.beans.BeanUtils;


public class StudentMapperImpl implements StudentMapper {
    @Override
    public Student dtoToEntity(StudentDto dto) {
        Student student  = new Student();
        BeanUtils.copyProperties(dto,student);
        return student;
    }

    @Override
    public StudentDto entityToDto(Student entity) {
        StudentDto dto = new StudentDto();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }
}
