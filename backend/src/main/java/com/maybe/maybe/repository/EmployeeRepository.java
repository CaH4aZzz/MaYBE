package com.maybe.maybe.repository;

import com.maybe.maybe.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeById(Long id);
    Employee findEmployeeByLogin(String login);
}