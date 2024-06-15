package org.aptech.t2208e.service.impl;

import org.aptech.t2208e.dto.DepartmentDto;
import org.aptech.t2208e.entity.Department;
import org.aptech.t2208e.mapper.DepartmentMapper;
import org.aptech.t2208e.mapper.impl.DepartmentMapperImpl;
import org.aptech.t2208e.repository.DepartmentRepository;
import org.aptech.t2208e.repository.impl.DepartmentRepositoryImpl;
import org.aptech.t2208e.service.DepartmentService;

import java.util.Optional;

public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository departmentRepository = new DepartmentRepositoryImpl();
    DepartmentMapper departmentMapper = new DepartmentMapperImpl();

    @Override
    public Optional<DepartmentDto> createDepartment(DepartmentDto departmentDto) {
        Optional<Department> optionalDepartment = departmentRepository.createDepartment(departmentDto);
        return optionalDepartment.map(departmentMapper::entityToDto);
    }

    @Override
    public void setChiefAndDeputyId(String position, Long employeeId, int id) {
        departmentRepository.setChiefAndDeputyId(position, employeeId, id);
    }

    @Override
    public void updateEmployeeNumber(String option, int id) {
        departmentRepository.updateEmployeeNumber(option, id);
    }
}
