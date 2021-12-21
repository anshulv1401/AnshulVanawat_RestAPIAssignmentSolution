package com.greatlearning.ems.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.ems.entity.Role;
import com.greatlearning.ems.spi.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping()
	public String post(@RequestBody() String name) {

		var roleFromDB = roleService.findByName(name);
		if (roleFromDB.isPresent()) {
			return String.format("Role %s already exists", name);
		}
		roleService.save(new Role(name));
		return String.format("Role %s Saved Successfully", name);
	}

	@PutMapping
	public Role put(@RequestParam int theId, @RequestBody String name) {

		var roleFromDB = roleService.findById(theId);

		if (roleFromDB.isPresent()) {
			var newRole = roleFromDB.get();
			newRole.setName(name);
			roleService.save(newRole);
			return newRole;
		} else {
			var newRole = new Role(name);
			roleService.save(newRole);
			return newRole;
		}
	}

	@DeleteMapping
	public String delete(@RequestParam("id") int theId) {
		var roleFromDB = roleService.findById(theId);
		if (roleFromDB.isEmpty()) {
			return String.format("Role with id %s does not exists", theId);
		}
		roleService.deleteById(theId);
		return String.format("Role with id %s Deleted Successfully", theId);
	}

	@GetMapping
	public List<Role> get(@RequestPart Optional<Integer> id) {

		if (id.isEmpty())
			return roleService.findAll();
		else {
			List<Role> roles = new ArrayList<Role>();
			var role = roleService.findById(id.get());
			if(role.isPresent())
				roles.add(role.get());
			return roles;
		}
	}
}
