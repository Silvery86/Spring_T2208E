package org.aptech.t2208e.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.aptech.t2208e.jpaRepository.annotation.Column;
import org.aptech.t2208e.jpaRepository.annotation.Entity;
import org.aptech.t2208e.jpaRepository.annotation.Id;

import java.util.Date;

@Getter
@Setter

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "employee")
public class Employee {
    @Id(name =  "id")
    private Long id;

    @Column(columnName = "full_name")
    private String fullName;

    @Column(columnName = "address")
    private String address;

    @Column(columnName = "date_of_birth")
    private Date dateOfBirth;

    @Column(columnName = "base_salary")
    private double baseSalary;

    @Column(columnName = "net_salary")
    private double netSalary;

    @Column(columnName = "insurance_base")
    private double insuranceBase;

    @Column(columnName = "department_id")
    private int departmentId;

    @Column(columnName = "position")
    private String position;

    @Column(columnName = "email")
    private String email;

    @Column(columnName = "is_off")
    private Boolean isOff;

}
