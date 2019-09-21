package com.ocha.boc.repository;

import com.ocha.boc.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Optional<Employee> findEmployeeByUsername(String username);

    List<Employee> findAllByRestaurantId(String restaurantId);

    Optional<Employee> findEmployeeById(String employeeId);

    boolean existsByUsername(String username);

    boolean existsById(String employeeId);
}
