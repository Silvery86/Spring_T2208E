package org.aptech.t2208e.service.impl;

import org.aptech.t2208e.dto.EmployeeDto;
import org.aptech.t2208e.entity.Department;
import org.aptech.t2208e.entity.Employee;
import org.aptech.t2208e.mapper.EmployeeMapper;
import org.aptech.t2208e.mapper.impl.EmployeeMapperImpl;
import org.aptech.t2208e.repository.EmployeeRepository;
import org.aptech.t2208e.repository.impl.EmployeeRepositoryImpl;
import org.aptech.t2208e.service.EmployeeService;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;


public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();
    EmployeeMapper employeeMapper = new EmployeeMapperImpl();
    @Override
    public Optional<EmployeeDto> createEmployee(EmployeeDto employeeDto) {
        Optional<Employee> optionalEmployee = employeeRepository.createEmployee(employeeDto);
        return optionalEmployee.map(employeeMapper::entityToDto);
    }

    @Override
    public Optional<EmployeeDto> updateEmployee(Long id, EmployeeDto employeeDto) {
        Optional<Employee> optionalEmployee = employeeRepository.updateEmployee(id, employeeDto);
        return optionalEmployee.map(employeeMapper::entityToDto);
    }

    @Override
    public EmployeeDto findById(Long id) {
        Optional<List<Employee>> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()){
            List<Employee> employees = optionalEmployee.get();
            if(!CollectionUtils.isEmpty(employees)){
                return employeeMapper.entityToDto(employees.get(0));
            }
        }
        System.err.println("Employee not exist with ID" + id);
        return null;
    }
}
