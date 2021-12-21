package com.greatlearning.ems.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greatlearning.ems.entity.Employee;
import com.greatlearning.ems.entity.User;
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
	public Optional<Employee> findById(int theId) {
		return employeeRepository.findById(theId);
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

	@Override
	public Optional<Employee> findByEmail(String theEmail) {
		
		Employee employeeWithEmail = new Employee();
		employeeWithEmail.setEmail(theEmail);
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("email", ExampleMatcher.GenericPropertyMatchers.ignoreCase())
				.withIgnorePaths("id", "name");

		Example<Employee> example = Example.of(employeeWithEmail, matcher);

		return employeeRepository.findOne(example);
	}
}
