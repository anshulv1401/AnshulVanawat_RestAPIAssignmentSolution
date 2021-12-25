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
import com.greatlearning.ems.exception.EmployeeNotFoundException;
import com.greatlearning.ems.spi.EmployeeService;
import com.greatlearning.ems.util.EmployeeHelper;

@RestController
@RequestMapping("/api/employees")
public class EmployeesController {

	@Autowired
	private EmployeeService employeeService;

	EmployeeHelper helper = new EmployeeHelper();

	@PostMapping("add")
	public String post(@RequestBody() EmployeeDto employeeDto) throws Exception {

		var employeeByEmail = employeeService.findByEmail(employeeDto.getEmail());
		if (employeeByEmail.isPresent()) {
			return String.format("Employee %s already Exist", employeeDto.getEmail());
		}

		Employee theEmployee = new Employee(employeeDto.getFirstName(), employeeDto.getLastName(),
				employeeDto.getEmail());

		if (!helper.isValid(theEmployee)) {
			throw new Exception("Employee details cannot be empty");
		}
		
		employeeService.save(theEmployee);

		return String.format("Employee %1$s %2$s Saved Successfully", employeeDto.getFirstName(),
				employeeDto.getLastName());
	}

	@GetMapping
	public List<Employee> get() {
		return employeeService.findAll();
	}

	@GetMapping("{id}")
	public Employee get(@PathVariable long id) {
		return employeeService.findById(id)
			      .orElseThrow(() -> new EmployeeNotFoundException(id));
	}

	@PutMapping("{id}")
	public Employee put(@RequestBody EmployeeDto employeeDto, @PathVariable long id) throws Exception {

		var employeeFromDb = employeeService.findById(id);

		Employee employee;
		if (employeeFromDb.isPresent()) {
			employee = employeeFromDb.get();
			employee.setEmail(employeeDto.getEmail());
			employee.setFirstName(employeeDto.getFirstName());
			employee.setLastName(employeeDto.getLastName());
			
			if (!helper.isValid(employee)) {
				throw new Exception("Employee details cannot be empty");
			}
			
			employeeService.save(employee);
		} else {
			employee = new Employee(id, employeeDto.getFirstName(), employeeDto.getLastName(), employeeDto.getEmail());
			
			if (!helper.isValid(employee)) {
				throw new Exception("Employee details cannot be empty");
			}
			
			employeeService.insert(employee);
		}
		
		return employee;
	}

	@DeleteMapping("{id}")
	public String delete(@PathVariable long id) {
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
