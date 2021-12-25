package com.greatlearning.ems.util;

import com.greatlearning.ems.entity.Role;

public class RoleHelper {

	public static boolean isValid(Role role) {
		if (isEmplyOrNull(role.getName())) {
			return false;
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
