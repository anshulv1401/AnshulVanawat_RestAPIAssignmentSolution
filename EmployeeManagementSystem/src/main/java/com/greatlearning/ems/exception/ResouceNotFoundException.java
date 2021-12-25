package com.greatlearning.ems.exception;

public class ResouceNotFoundException extends RuntimeException {

	public ResouceNotFoundException(Class resourse, Long id) {
		super(String.format("Could not find %1$s %2$s", resourse.getSimpleName(), id));
	}
}
