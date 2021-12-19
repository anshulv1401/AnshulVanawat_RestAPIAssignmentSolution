//package com.greatlearning.ems.controller;
//
//import java.security.Principal;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.greatlearning.ems.entity.Employee;
//import com.greatlearning.ems.spi.EmployeeService;
//
//@Controller
//@RequestMapping("views/employees")
//public class EmployeeController {
//	@Autowired
//	private EmployeeService employeeService;
//
//	@RequestMapping("/list")
//	public String listEmployees(Model theModel) {
//		List<Employee> theEmployees = employeeService.findAll();
//		theModel.addAttribute("Employees", theEmployees);
//		return "list-Employees";
//	}
//
//	@RequestMapping("/showFormForAdd")
//	public String showFormForAdd(Model theModel) {
//		Employee theEmployee = new Employee();
//		theModel.addAttribute("Employee", theEmployee);
//		return "Employee-form";
//	}
//
//	@RequestMapping("/showFormForUpdate")
//	public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {
//		Employee theEmployee = employeeService.findById(theId);
//		theModel.addAttribute("Employee", theEmployee);
//		return "Employee-form";
//	}
//
//	@PostMapping("/save")
//	public String saveEmployee(@RequestParam("id") int id, @RequestParam("firstName") String firstName,
//			@RequestParam("lastName") String lastName, @RequestParam("email") String email) {
//
//		Employee theEmployee;
//		if (id != 0) {
//			theEmployee = employeeService.findById(id);
//			theEmployee.setFirstName(firstName);
//			theEmployee.setLastName(lastName);
//			theEmployee.setEmail(email);
//		} else
//			theEmployee = new Employee(firstName, lastName, email);
//
//		employeeService.save(theEmployee);
//		return "redirect:/employees/list";
//
//	}
//
//	@RequestMapping("/delete")
//	public String delete(@RequestParam("employeeId") int theId) {
//		employeeService.deleteById(theId);
//		return "redirect:/employees/list";
//
//	}
//
//	@RequestMapping("/search")
//	public String search(@RequestParam("firstName") String firstName, Model theModel) {
//		if (firstName.trim().isEmpty()) {
//			return "redirect:/employees/list";
//		} else {
//			List<Employee> theEmployees = employeeService.searchBy(firstName);
//			theModel.addAttribute("Employees", theEmployees);
//			return "list-Employees";
//		}
//	}
//
//	@RequestMapping(value = "/403")
//	public ModelAndView accesssDenied(Principal user) {
//		ModelAndView model = new ModelAndView();
//
//		if (user != null) {
//			model.addObject("msg", "Hi " + user.getName() + ", you do not have permission to access this page!");
//		} else {
//			model.addObject("msg", "You do not have permission to access this page!");
//		}
//
//		model.setViewName("403");
//		return model;
//	}
//}
