package org.aptech.t2208e.repository;

import org.aptech.t2208e.entity.Employee;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> getAll();
}
