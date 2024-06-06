package org.aptech.t2208e.jpaRepository.main.impl;

import org.aptech.t2208e.dto.StudentDto;
import org.aptech.t2208e.entity.Student;
import org.aptech.t2208e.jpaRepository.annotation.Entity;
import org.aptech.t2208e.jpaRepository.consts.SqlQueryConstants;
import org.aptech.t2208e.jpaRepository.main.JpaExecutor;
import org.aptech.t2208e.utils.ConnectionPool;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class JpaExecutorImpl<T> implements JpaExecutor<T> {
    private Class<T> clazz;
    private String  className;
    public String tableName;
    Field[] fields;

    public static void main(String[] args) {

    }
    public JpaExecutorImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.className = clazz.getSimpleName();
        // get mapping of tableName
        this.tableName = clazz.getAnnotation(Entity.class) != null ?
                clazz.getAnnotation(Entity.class).tableName()
                : className;
        this.fields = clazz.getDeclaredFields();

        System.err.println(this.className);
        System.err.println(this.tableName);
        System.err.println(this.fields);
    }
    public abstract List<T> rowMapper(ResultSet rs);


    @Override
    public List<T> findAll() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        String sql  = SqlQueryConstants.SQL_SELECT_ALL.replace("%table_name%", this.tableName);
        PreparedStatement pt = null;
        try {
            pt = con.prepareStatement(sql);
            ResultSet rs = pt.executeQuery();
            return rowMapper(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<T> findById(Number id) {
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement pt = con.prepareStatement(SqlQueryConstants.SQL_SELECT_BY_ID
                     .replace("%table_name%", this.tableName))) {
            pt.setLong(1, id.longValue());
            try (ResultSet rs = pt.executeQuery()) {
                List<T> resultList = rowMapper(rs);
                if (!resultList.isEmpty()) {
                    return Optional.of(resultList.get(0));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while executing findById", e);
        }
        return Optional.empty();
    }


    public List<T> searchByFirstName(String firstName) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        String sql = SqlQueryConstants.SQL_SEARCH_BY_FIRST_NAME
                .replace("%table_name%", this.tableName)
                .replace("%first_name%", firstName);
        try (PreparedStatement pt = con.prepareStatement(sql)) {
            ResultSet rs = pt.executeQuery();
            return rowMapper(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<T> createStudent(StudentDto studentDto) {
        String sql = SqlQueryConstants.SQL_INSERT_DATA.replace("%table_name%", this.tableName);

        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, studentDto.getFirstName());
            pstmt.setString(2, studentDto.getLastName());
            pstmt.setString(3, studentDto.getAddress());
            pstmt.setInt(4, studentDto.getAge());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long generatedId = generatedKeys.getLong(1);
                        studentDto.setId(generatedId);
                    }
                }
                // Create and return a Student entity with the generated ID
                Student studentEntity = new Student();
                studentEntity.setId(studentDto.getId());
                studentEntity.setFirstName(studentDto.getFirstName());
                studentEntity.setLastName(studentDto.getLastName());
                studentEntity.setAddress(studentDto.getAddress());
                studentEntity.setAge(studentDto.getAge());
                return (Optional<T>) Optional.of(studentEntity);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating student: " + e.getMessage(), e);
        }
        return Optional.empty();
    }


}
