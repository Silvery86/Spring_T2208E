package org.aptech.t2208e.repository.impl;

import org.aptech.t2208e.dto.EmployeeDto;
import org.aptech.t2208e.entity.Department;
import org.aptech.t2208e.entity.Employee;
import org.aptech.t2208e.repository.EmployeeRepository;
import org.aptech.t2208e.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    private static final String SQL_QUERY_GET_ALL = "SELECT * FROM Employee WHERE is_off = false LIMIT ? OFFSET ?";
    private  static final String SQL_FIND_BY_ID = "SELECT * FROM Employee WHERE id = ?";
    private static final String SQL_QUERY_CREATE_EMPLOYEE = "INSERT INTO Employee (full_name, address, date_of_birth, base_salary, net_salary, insurance_base, department_id, position, email, is_off) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_QUERY_UPDATE_EMPLOYEE = "UPDATE Employee SET full_name = ?, address = ?, date_of_birth = ?, base_salary = ?, net_salary = ?, insurance_base = ?, department_id = ?, position = ?, email = ?, is_off = ? WHERE id = ?";
    private static final String SQL_FIND_ALL_EMPLOYEES = "SELECT * FROM Employee WHERE is_off = ?";
    private static final String SQL_ERROR_DUPLICATE_KEY = "23000";


    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection con = connectionPool.getConnection();
             PreparedStatement pt = con.prepareStatement(SQL_FIND_ALL_EMPLOYEES)) {

            pt.setBoolean(1, false); // Set parameter for is_off = false
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getLong("id"));
                employee.setFullName(rs.getString("full_name"));
                employee.setAddress(rs.getString("address"));
                employee.setDateOfBirth(rs.getDate("date_of_birth"));
                employee.setBaseSalary(rs.getDouble("base_salary"));
                employee.setNetSalary(rs.getDouble("net_salary"));
                employee.setInsuranceBase(rs.getDouble("insurance_base"));
                employee.setDepartmentId(rs.getInt("department_id"));
                employee.setPosition(rs.getString("position"));
                employee.setEmail(rs.getString("email"));
                employee.setIsOff(rs.getBoolean("is_off"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error get all employees", e);
        }
        return employees;
    }

    @Override
    public List<Employee> getAll(int start, int size) {
        List<Employee> employees = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try (PreparedStatement pt = con.prepareStatement(SQL_QUERY_GET_ALL)) {
            pt.setInt(1, size);
            pt.setInt(2, start);
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getLong("id"));
                employee.setFullName(rs.getString("full_name"));
                employee.setAddress(rs.getString("address"));
                employee.setDateOfBirth(rs.getDate("date_of_birth"));
                employee.setBaseSalary(rs.getDouble("base_salary"));
                employee.setNetSalary(rs.getDouble("net_salary"));
                employee.setInsuranceBase(rs.getDouble("insurance_base"));
                employee.setDepartmentId(rs.getInt("department_id"));
                employee.setPosition(rs.getString("position"));
                employee.setEmail(rs.getString("email"));
                employee.setIsOff(rs.getBoolean("is_off"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Log or handle as needed
                }
            }
        }
        return employees;
    }

    @Override
    public Optional<Employee> createEmployee(EmployeeDto employeeDto) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try (PreparedStatement pt = con.prepareStatement(SQL_QUERY_CREATE_EMPLOYEE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pt.setString(1, employeeDto.getFullName());
            pt.setString(2, employeeDto.getAddress());
            pt.setDate(3, new java.sql.Date(employeeDto.getDateOfBirth().getTime()));
            pt.setDouble(4, employeeDto.getBaseSalary());
            pt.setDouble(5, employeeDto.getNetSalary());
            pt.setDouble(6, employeeDto.getInsuranceBase());
            pt.setInt(7, employeeDto.getDepartmentId());
            pt.setString(8, employeeDto.getPosition());
            pt.setString(9, employeeDto.getEmail());
            pt.setBoolean(10, employeeDto.getIsOff());
            int rowsAffected = pt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = pt.getGeneratedKeys()) {
                    if (rs.next()) {
                        long generatedId = rs.getLong(1); // Assuming ID is auto-generated as a long
                        Employee employee = new Employee();
                        employee.setId(generatedId);  // Set the generated ID to employee object
                        employee.setFullName(employeeDto.getFullName());
                        employee.setAddress(employeeDto.getAddress());
                        employee.setDateOfBirth(employeeDto.getDateOfBirth());
                        employee.setBaseSalary(employeeDto.getBaseSalary());
                        employee.setNetSalary(employeeDto.getNetSalary());
                        employee.setInsuranceBase(employeeDto.getInsuranceBase());
                        employee.setDepartmentId(employeeDto.getDepartmentId());
                        employee.setPosition(employeeDto.getPosition());
                        employee.setEmail(employeeDto.getEmail());
                        employee.setIsOff(employeeDto.getIsOff());

                        return Optional.of(employee);
                    }
                }
            }
            con.close();
        } catch (SQLException e) {
            if (SQL_ERROR_DUPLICATE_KEY.equals(e.getSQLState())) {
                String errorMessage = "Employee with email '" + employeeDto.getEmail() + "' already exists.";
                throw new RuntimeException(errorMessage);
            } else {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();

    }

    @Override
    public Optional<Employee> updateEmployee(Long id, EmployeeDto employeeDto) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try (PreparedStatement pt = con.prepareStatement(SQL_QUERY_UPDATE_EMPLOYEE)) {
            pt.setString(1, employeeDto.getFullName());
            pt.setString(2, employeeDto.getAddress());
            pt.setDate(3, new java.sql.Date(employeeDto.getDateOfBirth().getTime()));
            pt.setDouble(4, employeeDto.getBaseSalary());
            pt.setDouble(5, employeeDto.getNetSalary());
            pt.setDouble(6, employeeDto.getInsuranceBase());
            pt.setInt(7, employeeDto.getDepartmentId());
            pt.setString(8, employeeDto.getPosition());
            pt.setString(9, employeeDto.getEmail());
            pt.setBoolean(10, employeeDto.getIsOff());
            pt.setLong(11, id);
            int rowsAffected = pt.executeUpdate();
            if (rowsAffected > 0) {
                Employee employee = new Employee();
                employee.setId(id);
                employee.setFullName(employeeDto.getFullName());
                employee.setAddress(employeeDto.getAddress());
                employee.setDateOfBirth(employeeDto.getDateOfBirth());
                employee.setBaseSalary(employeeDto.getBaseSalary());
                employee.setNetSalary(employeeDto.getNetSalary());
                employee.setInsuranceBase(employeeDto.getInsuranceBase());
                employee.setDepartmentId(employeeDto.getDepartmentId());
                employee.setPosition(employeeDto.getPosition());
                employee.setEmail(employeeDto.getEmail());
                employee.setIsOff(employeeDto.getIsOff());

                return Optional.of(employee);
            }
        } catch (SQLException e) {
            if (SQL_ERROR_DUPLICATE_KEY.equals(e.getSQLState())) {
                String errorMessage = "Employee with email '" + employeeDto.getEmail() + "' already exists.";
                throw new RuntimeException(errorMessage);
            } else {
                throw new RuntimeException(e);
            }
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Log or handle as needed
                }
            }
        }
        System.err.println("Employee is not exist with Id:" + id);
        return Optional.empty();
    }

    @Override
    public Optional<List<Employee>> findById(Long id) {
        List<Employee> employees = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        try{
            PreparedStatement pt = con.prepareStatement(SQL_FIND_BY_ID);
            pt.setLong(1,id);
            ResultSet rs = pt.executeQuery();
            while (rs.next()){
                Employee employee = new Employee();
                employee.setId(rs.getLong("id"));
                employee.setFullName(rs.getString("full_name"));
                employee.setAddress(rs.getString("address"));
                employee.setDateOfBirth(rs.getDate("date_of_birth"));
                employee.setBaseSalary(rs.getDouble("base_salary"));
                employee.setNetSalary(rs.getDouble("net_salary"));
                employee.setInsuranceBase(rs.getDouble("insurance_base"));
                employee.setDepartmentId(rs.getInt("department_id"));
                employee.setPosition(rs.getString("position"));
                employee.setEmail(rs.getString("email"));
                employee.setIsOff(rs.getBoolean("is_off"));
                employees.add(employee);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Log or handle as needed
                }
            }
        }
        return Optional.of(employees);
    }
}
