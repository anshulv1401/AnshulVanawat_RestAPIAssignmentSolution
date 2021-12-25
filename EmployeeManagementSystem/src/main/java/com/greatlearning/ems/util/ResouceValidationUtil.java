package com.greatlearning.ems.util;

import org.springframework.data.util.Pair;

import com.greatlearning.ems.entity.Employee;
import com.greatlearning.ems.entity.Role;
import com.greatlearning.ems.entity.User;

public class ResouceValidationUtil {

	public static Pair<Boolean, String> isValid(Employee employee) {
		if (isEmplyOrNull(employee.getFirstName())) {
			return Pair.of(false, "FirstName cannot be null");
		}
		if (isEmplyOrNull(employee.getLastName())) {
			return Pair.of(false, "LastName cannot be null");
		}
		if (isEmplyOrNull(employee.getEmail())) {
			return Pair.of(false, "Email cannot be null");
		}
		return Pair.of(true, "Valid");
	}

	public static Pair<Boolean, String> isValid(User user) {
		if (isEmplyOrNull(user.getUsername())) {
			return Pair.of(false, "UserName cannot be null");
		}
		if (isEmplyOrNull(user.getPassword())) {
			return Pair.of(false, "Password cannot be null");
		}

		for (Role role : user.getRoles()) {
			var validation = isValid(role);

			if (!validation.getFirst()) {
				return Pair.of(false, "Invalid role details : " + validation.getSecond());
			}
		}
		return Pair.of(true, "Valid");
	}

	public static Pair<Boolean, String> isValid(Role role) {
		if (isEmplyOrNull(role.getName())) {
			return Pair.of(false, "Name cannot be null");
		}
		return Pair.of(true, "Valid");
	}

	private static boolean isEmplyOrNull(String string) {
		if (string == null || string.isEmpty() || string.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
