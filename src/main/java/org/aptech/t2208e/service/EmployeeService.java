package org.aptech.t2208e.service;

import org.aptech.t2208e.dto.EmployeeDto;
import org.aptech.t2208e.entity.Employee;

import java.util.Optional;

public interface EmployeeService {
    Optional<EmployeeDto> createEmployee(EmployeeDto employeeDto);
    Optional<EmployeeDto> updateEmployee(Long id, EmployeeDto employeeDto);
    EmployeeDto findById(Long id);
}
