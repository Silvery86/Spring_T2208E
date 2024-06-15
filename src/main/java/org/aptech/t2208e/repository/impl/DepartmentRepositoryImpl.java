package org.aptech.t2208e.repository.impl;

import org.aptech.t2208e.dto.DepartmentDto;
import org.aptech.t2208e.entity.Department;
import org.aptech.t2208e.repository.DepartmentRepository;
import org.aptech.t2208e.utils.ConnectionPool;

import java.sql.*;
import java.util.Optional;

public class DepartmentRepositoryImpl implements DepartmentRepository {
    private static final String SQL_QUERY_CREATE_DEPARTMENT = "INSERT INTO Department (department_name, room_number, number_employee, chief_id, deputy_1_id, deputy_2_id) VALUES (?, ?, 0, NULL, NULL, NULL);";
    private static final String SQL_UPDATE_EMPLOYEE_NUMBER_PLUS = "UPDATE Department SET number_employee = number_employee + 1 WHERE id = ?";
    private static final String SQL_UPDATE_EMPLOYEE_NUMBER_MINUS = "UPDATE Department SET number_employee = number_employee - 1 WHERE id = ? AND number_employee > 0";
    private static final String SQL_UPDATE_CHIEF_ID = "UPDATE Department SET chief_id = ? WHERE id = ? AND chief_id IS NULL";
    private static final String SQL_UPDATE_DEPUTY_1_ID = "UPDATE Department SET deputy_1_id = ? WHERE id = ? AND deputy_1_id IS NULL";
    private static final String SQL_UPDATE_DEPUTY_2_ID = "UPDATE Department SET deputy_2_id = ? WHERE id = ? AND deputy_1_id IS NOT NULL AND deputy_2_id IS NULL";
    private static final String SQL_ERROR_DUPLICATE_KEY = "23000";

    ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public Optional<Department> createDepartment(DepartmentDto departmentDto) {
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

    @Override
    public void setChiefAndDeputyId(String position, Long employeeId, int id) {
        Connection con = connectionPool.getConnection();
        try {
            if (position.equalsIgnoreCase("Chief")) {
                try (PreparedStatement pt = con.prepareStatement(SQL_UPDATE_CHIEF_ID)) {
                    pt.setLong(1, employeeId);
                    pt.setInt(2, id);
                    int rowsAffected = pt.executeUpdate();
                    if (rowsAffected == 0) {
                        throw new RuntimeException("Chief ID is already set or department does not exist.");
                    }
                    System.out.println("Successfully updated chief_id.");
                }
            } else if (position.equalsIgnoreCase("Deputy")) {
                try (PreparedStatement pt1 = con.prepareStatement(SQL_UPDATE_DEPUTY_1_ID)) {
                    pt1.setLong(1, employeeId);
                    pt1.setInt(2, id);
                    int rowsAffected = pt1.executeUpdate();
                    if (rowsAffected == 0) {
                        try (PreparedStatement pt2 = con.prepareStatement(SQL_UPDATE_DEPUTY_2_ID)) {
                            pt2.setLong(1, employeeId);
                            pt2.setInt(2, id);
                            rowsAffected = pt2.executeUpdate();
                            if (rowsAffected == 0) {
                                throw new RuntimeException("All deputy positions are already set or department does not exist.");
                            }
                            System.out.println("Successfully updated deputy_2_id.");
                        }
                    } else {
                        System.out.println("Successfully updated deputy_1_id.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating chief or deputy ID", e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateEmployeeNumber(String option, int id) {
        String sqlUpdate;
        if ("Plus".equalsIgnoreCase(option)) {
            sqlUpdate = SQL_UPDATE_EMPLOYEE_NUMBER_PLUS;
        } else if ("Minus".equalsIgnoreCase(option)) {
            sqlUpdate = SQL_UPDATE_EMPLOYEE_NUMBER_MINUS;
        } else {
            throw new IllegalArgumentException("Invalid option: " + option);
        }

        try (Connection con = connectionPool.getConnection();
             PreparedStatement pt = con.prepareStatement(sqlUpdate)) {

            pt.setInt(1, id);
            int rowsAffected = pt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No rows updated. Department with id=" + id + " not found or numberEmployee is already 0.");
            } else {
                System.out.println("Successfully updated " + rowsAffected + " rows.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating employee number", e);
        }
    }
}
