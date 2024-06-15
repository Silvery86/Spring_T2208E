package org.aptech.t2208e.repository;


import org.aptech.t2208e.dto.EmployeeDto;
import org.aptech.t2208e.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    List<Employee> getAll();
    List<Employee> getAll(int start, int size);
    Optional<Employee> createEmployee(EmployeeDto employeeDto);
    Optional<Employee> updateEmployee(Long id, EmployeeDto employeeDto);
    Optional<List<Employee>> findById(Long id);
    boolean removeEmployee(Long id);
}
