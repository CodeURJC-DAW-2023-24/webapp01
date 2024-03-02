package com.tatademy.controller;

import java.security.Principal;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.tatademy.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			model.addAttribute("logged", true);
			model.addAttribute("admin", request.isUserInRole("ADMIN"));
			model.addAttribute("user", request.isUserInRole("USER"));
			model.addAttribute("userName", userService.findNameByEmail(principal.getName()));
			byte[] imageBlob = userService.findImageByEmail(principal.getName());
			if (imageBlob != null) {
				String base64Image = Base64.getEncoder().encodeToString(imageBlob);
				model.addAttribute("userImage", base64Image);
			}
		}
	}

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("home", true);
		return "home";
	}

	@GetMapping("/faq")
	public String faq(Model model) {
		model.addAttribute("faq", true);
		return "faq";
	}

}
