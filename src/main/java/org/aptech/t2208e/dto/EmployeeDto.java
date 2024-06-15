package org.aptech.t2208e.dto;

import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
public class EmployeeDto {
    private Long id;
    private String fullName;
    private String address;
    private Date dateOfBirth;
    private double baseSalary;
    private double netSalary;
    private double insuranceBase;
    private int departmentId;
    private String position;
}
