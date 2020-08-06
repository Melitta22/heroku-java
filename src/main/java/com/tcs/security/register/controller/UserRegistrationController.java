package com.tcs.security.register.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tcs.security.dto.UserDetailDTO;
import com.tcs.security.register.service.UserRegistrationService;

@RestController
@RequestMapping("/bigw/add")
public class UserRegistrationController {
	
	@Autowired
	UserRegistrationService service;
	
	@GetMapping("/validateUser")
	public boolean validateUser(@RequestParam(name = "dn") String user,
			@RequestParam(name = "password") String password) {
		System.out.println("User Details: "+user+" "+password);
		try {
			return service.validateUserLDAP(user, password);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@PostMapping("/addUser")
	public boolean addNewUser(@RequestBody UserDetailDTO user) {
		System.out.println("User Details: "+user.toString());
		try {
			service.addNewUserToLDAP(user);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@PostMapping("/displayUser")
	public UserDetailDTO displayUser(@RequestBody String uid) {
		try {
			return service.fetchUserDetails(uid);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@PostMapping("/updateUser")
	public boolean updateUser(@RequestBody UserDetailDTO user) {
		try {
			//UserDetailDTO user = service.fetchUserDetails(uid);
			service.updateUserDetails(user);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
