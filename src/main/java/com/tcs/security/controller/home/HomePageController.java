package com.tcs.security.controller.home;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tcs.security.dto.UserDetailDTO;

@RestController
public class HomePageController {
	
	@RequestMapping("/")
	public ModelAndView getHomePage() {
		return new ModelAndView("welcome");
	}
	
	@GetMapping(value = "/login")
	public ModelAndView login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;

	}
	
	@GetMapping("/newUser")
	public ModelAndView newUser() {
		return new ModelAndView("newUser");
	}
	
	@GetMapping("/updateUser")
	public ModelAndView updateUser() {
		return new ModelAndView("updateUser");
	}
	
	@ModelAttribute("userForm")
	public UserDetailDTO getUserDetailsObject() {
	return new UserDetailDTO();
	}

}
