package com.greatlearning.ems.util;

import com.greatlearning.ems.entity.Role;
import com.greatlearning.ems.entity.User;

public class UserHelper {

	public static boolean isValid(User user) {
		if (isEmplyOrNull(user.getUsername())) {
			return false;
		}
		if (isEmplyOrNull(user.getPassword())) {
			return false;
		}

		for (Role role : user.getRoles()) {
			if (!RoleHelper.isValid(role)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isEmplyOrNull(String string) {
		if (string == null || string.isEmpty() || string.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
