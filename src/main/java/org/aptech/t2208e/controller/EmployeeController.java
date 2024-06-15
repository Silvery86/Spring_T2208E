package org.aptech.t2208e.controller;

import org.aptech.t2208e.dto.DepartmentDto;
import org.aptech.t2208e.dto.EmployeeDto;
import org.aptech.t2208e.dto.ListEmployeeDto;
import org.aptech.t2208e.service.DepartmentService;
import org.aptech.t2208e.service.EmployeeService;
import org.aptech.t2208e.service.impl.DepartmentServiceImpl;
import org.aptech.t2208e.service.impl.EmployeeServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    DepartmentService departmentService = new DepartmentServiceImpl();
    EmployeeService employeeService = new EmployeeServiceImpl();


    @GetMapping("/employees")
    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> employeeDtos = employeeService.getAll();
        return employeeDtos;
    }

    @GetMapping("/employees/{page}")
    public List<ListEmployeeDto> getAllEmployees(@PathVariable int page) {

        int size = 10;
        int start = (page - 1) * size;
        List<EmployeeDto> employeeDtosPagination = employeeService.getAll(start, size);

        return employeeDtosPagination.stream()
                .map(this::convertToEmployeeListDto)
                .collect(Collectors.toList());
    }

    private ListEmployeeDto convertToEmployeeListDto(EmployeeDto employeeDto) {
        ListEmployeeDto listEmployeeDto = new ListEmployeeDto();
        listEmployeeDto.setId(employeeDto.getId());
        listEmployeeDto.setFullName(employeeDto.getFullName());
        listEmployeeDto.setAddress(employeeDto.getAddress());
        listEmployeeDto.setDateOfBirth(employeeDto.getDateOfBirth());
        listEmployeeDto.setBaseSalary(employeeDto.getBaseSalary());
        listEmployeeDto.setNetSalary(employeeDto.getNetSalary());
        listEmployeeDto.setInsuranceBase(employeeDto.getInsuranceBase());
        listEmployeeDto.setDepartmentName(employeeService.getDepartmentName(employeeDto.getDepartmentId()));
        listEmployeeDto.setChiefName(employeeService.getChiefName(employeeDto.getDepartmentId()));
        listEmployeeDto.setPosition(employeeDto.getPosition());
        listEmployeeDto.setEmail(employeeDto.getEmail());

        return listEmployeeDto;
    }
    @PutMapping(value = "/employee/create")
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

    @PutMapping(value = "/employee/update/{id}")
    public Optional<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
        EmployeeDto existEmployee = employeeService.findById(id);
        if(existEmployee.getIsOff()){
            System.err.println("Employee off can't return!");
            return Optional.empty();
        }
        if(
                !Objects.equals(existEmployee.getPosition(), employeeDto.getPosition())
                || "Chief".equalsIgnoreCase(existEmployee.getPosition())
                || "Deputy".equalsIgnoreCase(existEmployee.getPosition())
        ){
                if(!employeeDto.getIsOff()){
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

        }



        Optional<EmployeeDto> updatedEmployee = employeeService.updateEmployee(id, employeeDto);
        if (updatedEmployee.isPresent()) {

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
            if(employeeDto.getIsOff() && ("Chief".equalsIgnoreCase(employeeDto.getPosition()) || "Deputy".equalsIgnoreCase(employeeDto.getPosition()))){
                departmentService.removeChiefAndDeputyId(employeeDto.getPosition(),updatedEmployee.get().getId(),employeeDto.getDepartmentId());
                departmentService.updateEmployeeNumber("Minus",employeeDto.getDepartmentId());
            }
            return updatedEmployee;
        } else {
            return Optional.empty();
        }
    }
}
