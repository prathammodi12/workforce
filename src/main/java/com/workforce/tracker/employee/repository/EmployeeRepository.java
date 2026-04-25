package com.workforce.tracker.employee.repository;

import com.workforce.tracker.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
