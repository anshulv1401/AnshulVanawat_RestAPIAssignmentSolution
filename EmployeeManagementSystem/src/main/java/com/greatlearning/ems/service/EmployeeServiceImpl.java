package com.greatlearning.ems.service;

import java.util.ArrayList;
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
	public Optional<Employee> findById(long theId) {
		return employeeRepository.findById(theId);
	}

	@Override
	@Transactional
	public void save(Employee theEmployee) {
		employeeRepository.save(theEmployee);
	}

	@Override
	@Transactional
	public void deleteById(long theId) {
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
		switch (direction) {
		case ASC:
			return employeeRepository.findAll(Sort.by(Sort.Order.asc("firstName").ignoreCase()));
		case DESC:
			return employeeRepository.findAll(Sort.by(Sort.Order.desc("firstName").ignoreCase()));
		default:
			return new ArrayList<Employee>();
		}
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

	@Override
	@Transactional
	public void insert(Employee theEmployee) {

		if (theEmployee.getId() == null || theEmployee.getEmail() == null) {
			return;
		}

		employeeRepository.insertEmploye(theEmployee.getId(), theEmployee.getFirstName(), theEmployee.getLastName(),
				theEmployee.getEmail());
	}
}
