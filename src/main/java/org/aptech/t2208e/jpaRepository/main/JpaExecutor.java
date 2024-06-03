package org.aptech.t2208e.jpaRepository.main;

import java.util.List;
import java.util.Optional;

public interface JpaExecutor <T> {
    List<T> findAll();
    Optional<T> findById(Number id);
    List<T> searchByFirstName(String firstName);
}
