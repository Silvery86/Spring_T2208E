package org.aptech.t2208e.repository;

import org.aptech.t2208e.dto.DepartmentDto;
import org.aptech.t2208e.entity.Department;

import java.util.Optional;

public interface DepartmentRepository {
    Optional<Department> createDepartment(DepartmentDto departmentDto);
    void setChiefAndDeputyId(String position, Long employeeId, int id);
    void updateEmployeeNumber(String option, int id);
}
