package com.example.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    // Iterable<Employee> findByFirstName(String string);

    @Query("select e from Employee e where e.lastName like %?1")
    Iterable<Employee> findAllWhereName(String name);
}
