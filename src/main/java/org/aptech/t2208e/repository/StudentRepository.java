package org.aptech.t2208e.repository;

import org.aptech.t2208e.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    /**
     *
     * @param id : String
     * @return : list Dto query in table with id  =  ????
     */

    Optional<List<Student>> getById(String id);

    Optional<List<Student>> getByFirstName(String firstName);
    List<Student> getAll();

}
