package com.greatlearning.ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.ems.dto.UserDto;
import com.greatlearning.ems.entity.Role;
import com.greatlearning.ems.entity.User;
import com.greatlearning.ems.exception.ResouceInvalidException;
import com.greatlearning.ems.exception.ResouceNotFoundException;
import com.greatlearning.ems.spi.RoleService;
import com.greatlearning.ems.spi.UserService;
import com.greatlearning.ems.util.ResouceValidationUtil;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@PostMapping()
	@ResponseStatus(code = HttpStatus.CREATED)
	public String post(@RequestBody() UserDto userDto) {

		var userByUserName = userService.findByUserName(userDto.getUsername());
		if (userByUserName.isPresent()) {
			throw new ResouceInvalidException(User.class,
					String.format("UserName %s already Exist", userDto.getUsername()));
		}

		User newUser = new User(userDto.getUsername(), userDto.getPassword(), userDto.getRoles());
		var validation = ResouceValidationUtil.isValid(newUser);
		if (!validation.getFirst()) {
			throw new ResouceInvalidException(User.class, validation.getSecond());
		}

		for (Role role : userDto.getRoles()) {
			var roleFromDB = roleService.findById(role.getId());

			if (roleFromDB.isEmpty() || !roleFromDB.get().equals(role)) {
				throw new ResouceInvalidException(User.class,
						String.format("Role with details %s not found", role.toString()));
			}
		}

		userService.save(newUser);
		return String.format("User %s Created Successfully", userDto.getUsername());
	}

	@DeleteMapping("{id}")
	public String delete(@PathVariable int id) {
		var resouceById = userService.findById(id);
		if (resouceById.isEmpty()) {
			throw new ResouceNotFoundException(User.class, (long) id);
		}

		userService.deleteById(id);
		return String.format("User with id %s Deleted Successfully", id);
	}

	@GetMapping("{id}")
	public User get(@PathVariable int id) {
		return userService.findById(id).orElseThrow(() -> new ResouceNotFoundException(User.class, (long) id));
	}

	@GetMapping
	public List<User> get() {
		return userService.findAll();
	}
}
