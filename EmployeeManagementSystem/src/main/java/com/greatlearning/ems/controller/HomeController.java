package com.greatlearning.ems.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

	@GetMapping
	public String get() {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		return String.format("Welcome %s to Employee Management System", auth.getName());
	}

	@GetMapping("accessDenied")
	public String accessDenied() {
		return "Access Denied";
	}
}
