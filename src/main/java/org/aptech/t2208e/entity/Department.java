package org.aptech.t2208e.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.aptech.t2208e.jpaRepository.annotation.Column;
import org.aptech.t2208e.jpaRepository.annotation.Id;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id(name = "id")
    private int id;

    @Column(columnName = "department_name")
    private String depName;

    @Column(columnName = "room_number")
    private String roomNo;

    @Column(columnName = "number_employee")
    private int numberEmployee;

    @Column(columnName = "chief_id")
    private Long chiefId;

    @Column(columnName = "deputy_1_id")
    private Long deputyId1;

    @Column(columnName = "deputy_2_id")
    private Long deputyId2;

}
