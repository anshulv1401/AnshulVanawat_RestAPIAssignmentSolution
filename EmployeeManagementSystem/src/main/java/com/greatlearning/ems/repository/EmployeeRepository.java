package com.greatlearning.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greatlearning.ems.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
//	https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#query-by-example
	List<Employee> findByFirstNameContainsAllIgnoreCase(String firstName);
}
