package com.greatlearning.ems.dto;

import java.util.ArrayList;

import com.greatlearning.ems.entity.Role;

import lombok.Data;

@Data
public class UserDto {

	private String username;

	private String password;

	private ArrayList<Role> roles;

}
