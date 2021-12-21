package com.greatlearning.ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/employees")
public class EmployeesController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping()
	public String post(@RequestBody() EmployeeDto employeeDto) {

		var employeeByEmail = employeeService.findByEmail(employeeDto.getEmail());
		if(employeeByEmail.isPresent()) {
			return  String.format("Employee %s already Exist", employeeDto.getEmail());
		}
		
		Employee theEmployee = new Employee(employeeDto.getFirstName(), employeeDto.getLastName(),
				employeeDto.getEmail());
		
		employeeService.save(theEmployee);

		return String.format("Employee %1$s %2$s Saved Successfully", employeeDto.getFirstName(),
				employeeDto.getLastName());
	}

	@GetMapping("getAll")
	public List<Employee> getAll() {
		return employeeService.findAll();
	}

	@GetMapping("getById")
	public Employee getById(@RequestParam("employeeId") int theId) {
		return employeeService.findById(theId);
	}

	@GetMapping("getByFirstName")
	public List<Employee> getByFirstName(@RequestParam String firstName) {
		return employeeService.searchBy(firstName);
	}

	@GetMapping("getSort")
	public List<Employee> getSort(@RequestParam Direction direction) {
		return employeeService.sortBy(direction);
	}

	@PutMapping
	public Employee put(@RequestParam("employeeId") int theId, Employee employee) {
		employeeService.save(employee);
		return employee;
	}

	@DeleteMapping
	public String delete(@RequestParam("employeeId") int theId) {
		employeeService.deleteById(theId);
		return String.format("Employee %s Deleted Successfully", theId);
	}

}
