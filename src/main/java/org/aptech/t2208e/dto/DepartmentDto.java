package org.aptech.t2208e.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DepartmentDto {
    private int id;
    private String depName;
    private String roomNo;
    private int numberEmployee;
    private Long chiefId;
    private Long deputyId1;
    private Long deputyId2;
}
