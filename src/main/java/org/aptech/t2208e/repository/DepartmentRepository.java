package org.aptech.t2208e.repository;

import org.aptech.t2208e.dto.DepartmentDto;
import org.aptech.t2208e.entity.Department;

import java.util.Optional;

public interface DepartmentRepository {
    Optional<Department> createDepartment(DepartmentDto departmentDto);
}
