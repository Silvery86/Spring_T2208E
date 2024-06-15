package org.aptech.t2208e.controller;

import org.aptech.t2208e.dto.DepartmentDto;
import org.aptech.t2208e.dto.StudentDto;
import org.aptech.t2208e.service.DepartmentService;
import org.aptech.t2208e.service.impl.DepartmentServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class DepartmentController {
    DepartmentService departmentService = new DepartmentServiceImpl();
    @PutMapping(value = "department/create")
    public Optional<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto){
        System.out.println("Received studentDto: " + departmentDto);
        return departmentService.createDepartment(departmentDto);
    }

    @GetMapping(value = "department/{id}")
    public DepartmentDto findById(@PathVariable int id){
        DepartmentDto departmentDto = departmentService.findById(id);
        if(departmentDto != null){
            return departmentDto;
        }else{
            return null;
        }


    }
}
