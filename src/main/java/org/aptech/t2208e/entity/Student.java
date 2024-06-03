package org.aptech.t2208e.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.aptech.t2208e.jpaRepository.annotation.Column;
import org.aptech.t2208e.jpaRepository.annotation.Entity;
import org.aptech.t2208e.jpaRepository.annotation.FirstName;
import org.aptech.t2208e.jpaRepository.annotation.Id;

@Getter
@Setter

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "student")
public class Student {

    @Id(name =  "id") // meaning id : primary key
    private Long id;
    @Column(columnName = "first_name")

    @FirstName(name = "firstName")
    private String firstName;
    @Column(columnName = "last_name")
    private String lastName;
    @Column(columnName = "address")
    private String address;
    @Column(columnName = "age")
    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }
}
