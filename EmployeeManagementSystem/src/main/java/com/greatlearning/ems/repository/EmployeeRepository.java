package com.greatlearning.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.greatlearning.ems.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findByFirstNameContainsAllIgnoreCase(String firstName);

	@Modifying
	@Query(value = "insert into employee (id, first_name, last_name, email) values (?1, ?2, ?3, ?4)", nativeQuery = true)
	public void insertEmploye(long id, String firstName, String lastName, String email);
}
