package com.greatlearning.ems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greatlearning.ems.entity.Employee;
import com.greatlearning.ems.repository.EmployeeRepository;
import com.greatlearning.ems.spi.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	@Transactional
	public List<Employee> findAll() {
		List<Employee> employees = employeeRepository.findAll();
		return employees;
	}

	@Override
	@Transactional
	public Employee findById(int theId) {
		return employeeRepository.findById(theId).get();
	}

	@Override
	@Transactional
	public void save(Employee theEmployee) {
		employeeRepository.save(theEmployee);
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		employeeRepository.deleteById(theId);
	}

	@Override
	@Transactional
	public List<Employee> searchBy(String firstName) {
		List<Employee> employees = employeeRepository.findByFirstNameContainsAllIgnoreCase(firstName);
		return employees;
	}

	@Override
	public List<Employee> sortBy(Direction direction) {
		return employeeRepository.findAll(Sort.by(direction, "firstName"));
	}
}
