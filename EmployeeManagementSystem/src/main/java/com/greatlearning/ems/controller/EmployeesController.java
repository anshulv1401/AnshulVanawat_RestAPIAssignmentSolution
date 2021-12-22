package com.greatlearning.ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.ems.dto.EmployeeDto;
import com.greatlearning.ems.entity.Employee;
import com.greatlearning.ems.spi.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeesController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("add")
	public String post(@RequestBody() EmployeeDto employeeDto) {

		var employeeByEmail = employeeService.findByEmail(employeeDto.getEmail());
		if (employeeByEmail.isPresent()) {
			return String.format("Employee %s already Exist", employeeDto.getEmail());
		}

		Employee theEmployee = new Employee(employeeDto.getFirstName(), employeeDto.getLastName(),
				employeeDto.getEmail());

		employeeService.save(theEmployee);

		return String.format("Employee %1$s %2$s Saved Successfully", employeeDto.getFirstName(),
				employeeDto.getLastName());
	}

	@GetMapping
	public List<Employee> get() {
		return employeeService.findAll();
	}

	@GetMapping("{id}")
	public Employee get(@PathVariable int id) {
		var emp = employeeService.findById(id);
		return emp.get();
	}

	@PutMapping("{id}")
	public Employee put(@RequestBody EmployeeDto employeeDto, @PathVariable int id) {

		var employeeFromDb = employeeService.findById(id);

		Employee employee;
		if (employeeFromDb.isPresent()) {
			employee = employeeFromDb.get();
			employee.setEmail(employeeDto.getEmail());
			employee.setFirstName(employeeDto.getFirstName());
			employee.setLastName(employeeDto.getLastName());
			employeeService.save(employee);
		} else {
			employee = new Employee(id, employeeDto.getFirstName(), employeeDto.getLastName(), employeeDto.getEmail());
			employeeService.save(employee);
		}

		employeeService.save(employee);
		return employee;
	}

	@DeleteMapping("{id}")
	public String delete(@PathVariable int id) {
		employeeService.deleteById(id);
		return String.format("Employee %s Deleted Successfully", id);
	}
	
	@GetMapping("search/{firstName}")
	public List<Employee> get(@PathVariable String firstName) {
		return employeeService.searchBy(firstName);
	}

	@GetMapping("sort")
	public List<Employee> get(@RequestParam Direction order) {
		return employeeService.sortBy(order);
	}

}
