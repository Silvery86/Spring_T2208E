package org.aptech.t2208e.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentSearchRequestDto {
    private String firstName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
