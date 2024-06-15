package org.aptech.t2208e.controller;

import org.aptech.t2208e.dto.DepartmentDto;
import org.aptech.t2208e.repository.DepartmentRepository;
import org.aptech.t2208e.repository.impl.DepartmentRepositoryImpl;
import org.aptech.t2208e.service.DepartmentService;
import org.aptech.t2208e.service.impl.DepartmentServiceImpl;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        // Initialize repository and service
        DepartmentRepository departmentRepository = new DepartmentRepositoryImpl();
        DepartmentService departmentService = new DepartmentServiceImpl();

        // Test createDepartment method
        DepartmentDto newDepartment = new DepartmentDto();
        newDepartment.setDepName("Rsdsd232323dd4");
        newDepartment.setRoomNo("101");

        Optional<DepartmentDto> createdDepartment = departmentService.createDepartment(newDepartment);
        createdDepartment.ifPresent(department -> {
            System.out.println("Created Department: " + department);
        });


        departmentService.setChiefAndDeputyId("Deputy", 2123123L, createdDepartment.get().getId());
        System.out.println("Set Deputy ID for Department ID " + createdDepartment.get().getId());
        departmentService.setChiefAndDeputyId("Deputy", 21223233123L, createdDepartment.get().getId());
        System.out.println("Set Deputy ID for Department ID " + createdDepartment.get().getId());
        // Test updateEmployeeNumber method
        departmentService.updateEmployeeNumber("Plus", createdDepartment.get().getId());
        System.out.println("Increased number of employees for Department ID " + createdDepartment.get().getId());

        departmentService.updateEmployeeNumber("Minus", createdDepartment.get().getId());
        System.out.println("Decreased number of employees for Department ID " + createdDepartment.get().getId());
    }
}
