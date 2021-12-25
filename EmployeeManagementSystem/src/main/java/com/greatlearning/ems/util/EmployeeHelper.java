package com.greatlearning.ems.util;

import com.greatlearning.ems.entity.Employee;

public class EmployeeHelper {

	public boolean isValid(Employee employee) {
		if (isEmplyOrNull(employee.getFirstName())) {
			return false;
		}
		if (isEmplyOrNull(employee.getLastName())) {
			return false;
		}
		if (isEmplyOrNull(employee.getEmail())) {
			return false;
		}
		return true;
	}

	private boolean isEmplyOrNull(String string) {
		if (string == null || string.isEmpty() || string.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
