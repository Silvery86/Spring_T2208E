package org.aptech.t2208e.repository.impl;

import org.aptech.t2208e.entity.Employee;
import org.aptech.t2208e.repository.EmployeeRepository;

import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Override
    public List<Employee> getAll() {
        return List.of();
    }
}
