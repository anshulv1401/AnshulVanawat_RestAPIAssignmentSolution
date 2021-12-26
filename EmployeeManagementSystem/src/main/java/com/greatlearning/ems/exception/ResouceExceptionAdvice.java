package com.greatlearning.ems.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ResouceExceptionAdvice {

	@ResponseBody
	@ExceptionHandler(ResouceNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	String resourceNotFoundHandler(ResouceNotFoundException ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(ResouceInvalidException.class)
	@ResponseStatus(code = HttpStatus.CONFLICT)
	String resourceInvalidHandler(ResouceInvalidException ex) {
		return ex.getMessage();
	}
}
