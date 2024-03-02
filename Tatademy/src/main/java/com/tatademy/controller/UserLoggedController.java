package com.tatademy.controller;

import java.security.Principal;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tatademy.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserLoggedController {

	@Autowired
	private UserService userService;

	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
		Principal principal = request.getUserPrincipal();
		model.addAttribute("name", userService.findNameByEmail(principal.getName()));
		model.addAttribute("surname", userService.findNameByEmail(principal.getName()));
		model.addAttribute("logged", true);
		model.addAttribute("userProfileArea", true);
		model.addAttribute("admin", request.isUserInRole("ADMIN"));
		model.addAttribute("user", request.isUserInRole("USER"));
		model.addAttribute("userName", userService.findNameByEmail(principal.getName()));
		byte[] imageBlob = userService.findImageByEmail(principal.getName());
		if (imageBlob != null) {
			String base64Image = Base64.getEncoder().encodeToString(imageBlob);
			model.addAttribute("userImage", base64Image);
		}
	}

	@GetMapping("/user/setting-edit-profile")
	public String settingEditProfile() {
		return "setting-edit-profile";
	}

	@GetMapping("/user/setting-student-security")
	public String settingStudentSecurity(Model model, HttpServletRequest request) {
		return "setting-student-security";
	}

	@GetMapping("/user/setting-student-delete-profile")
	public String settingStudentDeleteProfile(Model model, HttpServletRequest request) {
		return "setting-student-delete-profile";
	}

	@PostMapping("/user/deleteAccount")
	public String userDeleteAccoun(HttpServletRequest request) {
		userService.delete(userService.findByEmail(request.getUserPrincipal().getName()));
		return "redirect:/user/logout";
	}

	@GetMapping("/user/logout")
	public String logout(HttpServletRequest request, HttpSession session) {
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		session.invalidate();
		logoutHandler.logout(request, null, null);
		return "redirect:/";
	}

}
