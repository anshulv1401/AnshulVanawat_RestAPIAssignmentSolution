package com.greatlearning.ems.exception;

public class ResouceInvalidException extends RuntimeException {

	public ResouceInvalidException(Class resourse, String conflictMsg) {
		super(String.format("%1$s details are not valid : %2$s", resourse.getSimpleName(), conflictMsg));
	}
}
