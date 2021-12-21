package com.greatlearning.ems.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.ems.entity.Role;
import com.greatlearning.ems.entity.User;
import com.greatlearning.ems.spi.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping()
	public String post(@RequestBody() String userName, @RequestBody() String password, @RequestBody List<Role> roles) {
		var userFromDB = userService.findByUserName(userName);

		if (userFromDB.isPresent()) {
			return String.format("User %s already exists", userName);
		}

		User newUser = new User(userName, password, roles);
		userService.save(newUser);

		return String.format("User %s Created Successfully", userName);
	}

	@DeleteMapping
	public String delete(@RequestParam int theId) {
		var userFromDB = userService.findById(theId);

		if (userFromDB.isEmpty()) {
			return String.format("User with id %s does not exists", theId);
		}

		userService.deleteById(theId);

		return String.format("User with id %s Deleted Successfully", theId);
	}

	@GetMapping
	public List<User> getById(int id) {

		List<User> users = new ArrayList<User>();
		var user = userService.findById(id);
		if (user.isPresent())
			users.add(user.get());
		return users;
	}

	@GetMapping
	public List<User> getAll() {
		return userService.findAll();
	}
}
