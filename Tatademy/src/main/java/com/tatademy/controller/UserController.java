package com.tatademy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tatademy.model.User;
import com.tatademy.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/forgot-password")
	public String forgotPassword() {
		return "forgot-password";
	}

	@PostMapping("/signup")
	public String signup(User user, RedirectAttributes attributes) {
		if (userService.existsByEmail(user.getEmail())) {
			// attributes.addFlashAttribute("msga", "Error al crear.");
			return "redirect:/register";
		} else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userService.save(user);
			return "redirect-/login";
		}
	}

}
