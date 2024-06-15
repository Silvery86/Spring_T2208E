package org.aptech.t2208e.service;

import org.aptech.t2208e.dto.DepartmentDto;

import java.util.Optional;

public interface DepartmentService {
    Optional<DepartmentDto> createDepartment(DepartmentDto departmentDto);
    void setChiefAndDeputyId(String position, Long employeeId, int id);

    void updateEmployeeNumber(String option, int id);
}
