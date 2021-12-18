package com.greatlearning.ems.spi;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.greatlearning.ems.entity.Employee;

public interface EmployeeService {
	public List<Employee> findAll();
	public Employee findById(int theId);
	public void save(Employee theEmployee);
	public void deleteById(int theId);
	public List<Employee> searchBy(String firstName);
	public List<Employee> sortBy(Direction direction);
}