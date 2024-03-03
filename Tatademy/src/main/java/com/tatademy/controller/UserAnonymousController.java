package com.tatademy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tatademy.model.User;
import com.tatademy.service.EmailService;
import com.tatademy.service.PasswordGeneratorService;
import com.tatademy.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserAnonymousController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
	}

	@RequestMapping("/login")
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

	@PostMapping("/forgot-password")
	public String forgotPassword(User user) {
		if (userService.existsByEmail(user.getEmail())) {
			User realUser = userService.findByEmail(user.getEmail());
			String password = PasswordGeneratorService.generateRandomPassword();
			emailService.sendEmail(realUser, password);
			realUser.setPassword(passwordEncoder.encode(password));
			userService.save(realUser);
			return "redirect:/login";
		} else {
			return "redirect:/forgot-password";
		}
	}

	@PostMapping("/signup")
	public String signup(User user, RedirectAttributes attributes) {
		if (userService.existsByEmail(user.getEmail())) {
			return "redirect:/register";
		} else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRoles(List.of("USER"));
			userService.save(user);
			return "redirect:/login";
		}
	}

}
