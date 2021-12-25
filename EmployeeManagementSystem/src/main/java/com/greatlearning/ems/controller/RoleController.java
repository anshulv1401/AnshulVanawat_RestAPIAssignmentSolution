package com.greatlearning.ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.ems.dto.RoleDto;
import com.greatlearning.ems.entity.Role;
import com.greatlearning.ems.spi.RoleService;
import com.greatlearning.ems.util.RoleHelper;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping
	public String post(@RequestBody() RoleDto role) {
		var newRole = new Role(role.getName());
		
		if (!RoleHelper.isValid(newRole)) {
			return "Role details cannot be empty : " + newRole.toString();
		}
		
		roleService.save(newRole);
		return String.format("Role %s Saved Successfully", role.getName());
	}

	@DeleteMapping("{id}")
	public String delete(@PathVariable int id) {
		roleService.deleteById(id);
		return String.format("Role with id %s Deleted Successfully", id);
	}

	@GetMapping("{id}")
	public Role get(@PathVariable int id) {
		return roleService.findById(id).get();
	}

	@GetMapping
	public List<Role> get() {
		return roleService.findAll();
	}
}
