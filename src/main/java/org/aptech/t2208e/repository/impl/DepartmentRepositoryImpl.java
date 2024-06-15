package org.aptech.t2208e.repository.impl;

import org.aptech.t2208e.dto.DepartmentDto;
import org.aptech.t2208e.entity.Department;
import org.aptech.t2208e.repository.DepartmentRepository;
import org.aptech.t2208e.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentRepositoryImpl implements DepartmentRepository {
    private static final String SQL_QUERY_CREATE_DEPARTMENT = "INSERT INTO Department (department_name, room_number, number_employee, chief_id, deputy_1_id, deputy_2_id) VALUES (?, ?, 0, NULL, NULL, NULL);";
    private static final String SQL_ERROR_DUPLICATE_KEY = "23000";

    @Override
    public Optional<Department> createDepartment(DepartmentDto departmentDto) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();

        try (PreparedStatement pt = con.prepareStatement(SQL_QUERY_CREATE_DEPARTMENT, Statement.RETURN_GENERATED_KEYS)) {
            pt.setString(1, departmentDto.getDepName());
            pt.setString(2, departmentDto.getRoomNo());

            int rowsAffected = pt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = pt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1); // Assuming ID is auto-generated as an int
                        Department department = new Department();
                        department.setId(generatedId);  // Set the generated ID to department object
                        department.setDepName(departmentDto.getDepName());
                        department.setRoomNo(departmentDto.getRoomNo());
                        department.setNumberEmployee(0);  // Default value
                        department.setChiefId(null);  // Default value
                        department.setDeputyId1(null);  // Default value
                        department.setDeputyId2(null);  // Default value

                        return Optional.of(department);
                    }
                }
            }
        } catch (SQLException e) {
            if (SQL_ERROR_DUPLICATE_KEY.equals(e.getSQLState())) {
                String errorMessage = "Department with name '" + departmentDto.getDepName() + "' already exists.";
                throw new RuntimeException(errorMessage);
            } else {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }
}
