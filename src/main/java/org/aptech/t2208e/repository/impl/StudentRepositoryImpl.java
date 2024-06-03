package org.aptech.t2208e.repository.impl;

import org.aptech.t2208e.entity.Student;
import org.aptech.t2208e.repository.StudentRepository;
import org.aptech.t2208e.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepositoryImpl  implements StudentRepository {


    @Override
    public Optional<List<Student>> getById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Student>> getByFirstName(String firstName) {
        return Optional.empty();
    }

    @Override
    public List<Student> getAll() {
        return List.of();
    }
    // extend vs implement
}
