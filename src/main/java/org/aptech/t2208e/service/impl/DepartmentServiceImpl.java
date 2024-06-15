package org.aptech.t2208e.service.impl;

import org.aptech.t2208e.dto.DepartmentDto;
import org.aptech.t2208e.entity.Department;
import org.aptech.t2208e.mapper.DepartmentMapper;
import org.aptech.t2208e.mapper.impl.DepartmentMapperImpl;
import org.aptech.t2208e.repository.DepartmentRepository;
import org.aptech.t2208e.repository.impl.DepartmentRepositoryImpl;
import org.aptech.t2208e.service.DepartmentService;
import org.springframework.util.CollectionUtils;

import java.util.List;
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

    @Override
    public DepartmentDto findById(int id) {
        Optional<List<Department>> optionalDepartment = departmentRepository.findById(id);
        if(optionalDepartment.isPresent()){
            List<Department> departments = optionalDepartment.get();
            if(!CollectionUtils.isEmpty(departments)){
                return departmentMapper.entityToDto(departments.get(0));
            }
        }
        System.err.println("Department not exist with ID" + id);
        return null;
    }

    @Override
    public void removeChiefAndDeputyId(String position, Long employeeId, int id) {
        departmentRepository.removeChiefAndDeputyId(position, employeeId, id);
    }
}
