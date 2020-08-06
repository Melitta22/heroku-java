package com.tcs.security.register.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tcs.security.dto.UserDetailDTO;
import com.tcs.security.register.service.UserRegistrationService;

@RestController
@RequestMapping("/jbigw")
public class JUserRegistrationController {
	
	@Autowired
	UserRegistrationService service;
	
	@PostMapping("/addUser")
	public ModelAndView addNewUser(@ModelAttribute("userForm") UserDetailDTO user) {
		System.out.println("User Details: "+user.toString());
		try {
			service.addNewUserToLDAP(user);
			return new ModelAndView("registerSuccess");
		} catch(Exception e) {
			ModelAndView model = new ModelAndView();
			model.addObject("error", "Registration was not Successfull !");
			model.setViewName("newUser");
			return model;
		}
		
	}
	
	@PostMapping("/displayUser")
	public ModelAndView displayUser(@ModelAttribute("userForm") UserDetailDTO user) {
		return new ModelAndView("displayUser");
	}

}
