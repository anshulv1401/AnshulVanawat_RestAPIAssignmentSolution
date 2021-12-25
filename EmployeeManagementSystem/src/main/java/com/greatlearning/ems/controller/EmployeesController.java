package com.greatlearning.ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.ems.dto.EmployeeDto;
import com.greatlearning.ems.entity.Employee;
import com.greatlearning.ems.exception.ResouceInvalidException;
import com.greatlearning.ems.exception.ResouceNotFoundException;
import com.greatlearning.ems.spi.EmployeeService;
import com.greatlearning.ems.util.ResouceValidationUtil;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping()
	@ResponseStatus(code = HttpStatus.CREATED)
	public String post(@RequestBody() EmployeeDto employeeDto) throws Exception {
		var employeeByEmail = employeeService.findByEmail(employeeDto.getEmail());
		if (employeeByEmail.isPresent()) {
			throw new ResouceInvalidException(Employee.class,
					String.format("Employee %s already Exist", employeeDto.getEmail()));
		}

		Employee theEmployee = new Employee(employeeDto.getFirstName(), employeeDto.getLastName(),
				employeeDto.getEmail());

		var validation = ResouceValidationUtil.isValid(theEmployee);
		if (!validation.getFirst()) {
			throw new ResouceInvalidException(Employee.class, validation.getSecond());
		}

		employeeService.save(theEmployee);
		return String.format("Employee %1$s %2$s Saved Successfully", employeeDto.getFirstName(),
				employeeDto.getLastName());
	}

	@PutMapping("{id}")
	public Employee put(@RequestBody EmployeeDto employeeDto, @PathVariable long id) throws Exception {
		Employee theEmployee = new Employee(employeeDto.getFirstName(), employeeDto.getLastName(),
				employeeDto.getEmail());

		var validation = ResouceValidationUtil.isValid(theEmployee);
		if (!validation.getFirst()) {
			throw new ResouceInvalidException(Employee.class, validation.getSecond());
		}

		var employeeFromDb = employeeService.findById(id);
		if (employeeFromDb.isPresent()) {
			theEmployee = employeeFromDb.get();
			theEmployee.setEmail(employeeDto.getEmail());
			theEmployee.setFirstName(employeeDto.getFirstName());
			theEmployee.setLastName(employeeDto.getLastName());
			employeeService.save(theEmployee);
		} else {
			theEmployee = new Employee(id, employeeDto.getFirstName(), employeeDto.getLastName(),
					employeeDto.getEmail());
			employeeService.insert(theEmployee);
		}

		return theEmployee;
	}

	@DeleteMapping("{id}")
	public String delete(@PathVariable long id) {
		var resouceById = employeeService.findById(id);
		if (resouceById.isEmpty()) {
			throw new ResouceNotFoundException(Employee.class, id);
		}

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

	@GetMapping
	public List<Employee> get() {
		return employeeService.findAll();
	}

	@GetMapping("{id}")
	public Employee get(@PathVariable long id) {
		return employeeService.findById(id).orElseThrow(() -> new ResouceNotFoundException(Employee.class, id));
	}

}
