package org.aptech.t2208e.controller;

import org.aptech.t2208e.dto.DepartmentDto;
import org.aptech.t2208e.dto.EmployeeDto;
import org.aptech.t2208e.service.DepartmentService;
import org.aptech.t2208e.service.EmployeeService;
import org.aptech.t2208e.service.impl.DepartmentServiceImpl;
import org.aptech.t2208e.service.impl.EmployeeServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    DepartmentService departmentService = new DepartmentServiceImpl();
    EmployeeService employeeService = new EmployeeServiceImpl();

    @PutMapping(value = "employee/create")
    public Optional<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        DepartmentDto departmentDto = departmentService.findById(employeeDto.getDepartmentId());
            // Check if position is Chief or Deputy and if the respective positions are already filled
            if ("Chief".equalsIgnoreCase(employeeDto.getPosition()) && departmentDto.getChiefId() != null) {
                System.out.println("Chief position already assigned for department ID " + employeeDto.getDepartmentId());
                return Optional.empty();
            }
            if ("Deputy".equalsIgnoreCase(employeeDto.getPosition())) {
                if (departmentDto.getDeputyId1() != null && departmentDto.getDeputyId2() != null) {
                    System.out.println("Both Deputy positions already assigned for department ID " + employeeDto.getDepartmentId());
                    return Optional.empty();
                }
            }
            Optional<EmployeeDto> createdEmployee = employeeService.createEmployee(employeeDto);
            if (createdEmployee.isPresent()) {
                // Update employee number in department
                departmentService.updateEmployeeNumber("Plus", createdEmployee.get().getDepartmentId());
                // Check if employee is Chief or Deputy and update respective IDs
                if ("Chief".equalsIgnoreCase(employeeDto.getPosition()) || "Deputy".equalsIgnoreCase(employeeDto.getPosition())) {
                    departmentService.setChiefAndDeputyId(employeeDto.getPosition(), createdEmployee.get().getId(), employeeDto.getDepartmentId());
                }
                return createdEmployee;
            } else {
                return Optional.empty();
            }
    }

    @PutMapping(value = "employee/update/{id}")
    public Optional<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
        EmployeeDto existEmployee = employeeService.findById(id);
        if(existEmployee.isOff()){
            System.err.println("Employee off cant not return!");
            return Optional.empty();
        }
        if(
                !Objects.equals(existEmployee.getPosition(), employeeDto.getPosition())
                || "Chief".equalsIgnoreCase(existEmployee.getPosition())
                || "Deputy".equalsIgnoreCase(existEmployee.getPosition())
        ){
                DepartmentDto departmentDto = departmentService.findById(employeeDto.getDepartmentId());
                if ("Chief".equalsIgnoreCase(employeeDto.getPosition()) && departmentDto.getChiefId() != null) {
                    System.out.println("Chief position already assigned for department ID " + employeeDto.getDepartmentId());
                    return Optional.empty();
                }
                if ("Deputy".equalsIgnoreCase(employeeDto.getPosition())) {
                    if (departmentDto.getDeputyId1() != null && departmentDto.getDeputyId2() != null) {
                        System.out.println("Both Deputy positions already assigned for department ID " + employeeDto.getDepartmentId());
                        return Optional.empty();
                    }
                }
        }



        Optional<EmployeeDto> updatedEmployee = employeeService.updateEmployee(id, employeeDto);
        if (updatedEmployee.isPresent()) {
            if(existEmployee.isOff() && ("Chief".equalsIgnoreCase(employeeDto.getPosition()) || "Deputy".equalsIgnoreCase(employeeDto.getPosition()))){
                departmentService.removeChiefAndDeputyId(employeeDto.getPosition(),updatedEmployee.get().getId(),employeeDto.getDepartmentId());
            }
            if ("Chief".equalsIgnoreCase(employeeDto.getPosition()) || "Deputy".equalsIgnoreCase(employeeDto.getPosition())) {
                departmentService.setChiefAndDeputyId(employeeDto.getPosition(), updatedEmployee.get().getId(), employeeDto.getDepartmentId());
            }
            if ("Chief".equalsIgnoreCase(existEmployee.getPosition()) || "Deputy".equalsIgnoreCase(existEmployee.getPosition())) {
                departmentService.removeChiefAndDeputyId(existEmployee.getPosition(), existEmployee.getId(), existEmployee.getDepartmentId());
            }
            if(existEmployee.getDepartmentId() != employeeDto.getDepartmentId()){
                departmentService.updateEmployeeNumber("Minus",existEmployee.getDepartmentId());
                departmentService.updateEmployeeNumber("Plus",employeeDto.getDepartmentId());
            }
            return updatedEmployee;
        } else {
            return Optional.empty();
        }
    }
}
