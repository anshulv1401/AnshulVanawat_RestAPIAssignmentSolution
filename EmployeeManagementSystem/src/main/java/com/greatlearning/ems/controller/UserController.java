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

import com.greatlearning.ems.dto.UserDto;
import com.greatlearning.ems.entity.User;
import com.greatlearning.ems.spi.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping()
	public String post(@RequestBody() UserDto userDto) {
		User newUser = new User(userDto.getUsername(), userDto.getPassword(), userDto.getRoles());
		userService.save(newUser);
		return String.format("User %s Created Successfully", userDto.getUsername());
	}

	@DeleteMapping("{id}")
	public String delete(@PathVariable int id) {
		userService.deleteById(id);
		return String.format("User with id %s Deleted Successfully", id);
	}

	@GetMapping("{id}")
	public User get(@PathVariable int id) {
		return userService.findById(id).get();
	}

	@GetMapping
	public List<User> get() {
		return userService.findAll();
	}
}
