package org.aptech.t2208e.mapper;

import org.aptech.t2208e.dto.DepartmentDto;
import org.aptech.t2208e.entity.Department;

public interface DepartmentMapper {
    Department dtoToEntity(DepartmentDto dto);
    DepartmentDto entityToDto(Department entity);
}
