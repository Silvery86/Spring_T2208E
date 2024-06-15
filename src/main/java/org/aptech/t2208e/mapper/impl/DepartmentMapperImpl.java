package org.aptech.t2208e.mapper.impl;

import org.aptech.t2208e.dto.DepartmentDto;
import org.aptech.t2208e.dto.EmployeeDto;
import org.aptech.t2208e.entity.Department;
import org.aptech.t2208e.entity.Employee;
import org.aptech.t2208e.mapper.DepartmentMapper;
import org.springframework.beans.BeanUtils;

public class DepartmentMapperImpl implements DepartmentMapper {
    @Override
    public Department dtoToEntity(DepartmentDto dto) {
        Department department  = new Department();
        BeanUtils.copyProperties(dto,department);
        return department;
    }

    @Override
    public DepartmentDto entityToDto(Department entity) {
        DepartmentDto dto = new DepartmentDto();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }
}
