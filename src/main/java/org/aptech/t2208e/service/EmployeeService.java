package org.aptech.t2208e.service;

import org.aptech.t2208e.dto.EmployeeDto;


import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Optional<EmployeeDto> createEmployee(EmployeeDto employeeDto);
    Optional<EmployeeDto> updateEmployee(Long id, EmployeeDto employeeDto);
    EmployeeDto findById(Long id);
    List<EmployeeDto> getAll();
    List<EmployeeDto> getAll(int start, int size);
    String getDepartmentName(int departmentId);
    String getChiefName(int departmentId);
}
