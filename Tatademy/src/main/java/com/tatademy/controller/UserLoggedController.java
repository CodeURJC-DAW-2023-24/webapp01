package com.tatademy.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tatademy.model.User;
import com.tatademy.repository.UserRepository;
import com.tatademy.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserLoggedController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

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
	public String settingEditProfile(Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		User user = userRepository.findByEmail(principal.getName());
		model.addAttribute("surname", user.getSurname());
		model.addAttribute("id", user.getId());
		model.addAttribute("email", user.getEmail());
		return "setting-edit-profile";
	}

	@PostMapping("/user/update-profile")
	public String updateProfile(@RequestParam Long id, @RequestParam("fileImage") MultipartFile fileImage,
			@RequestParam String name, @RequestParam String surname) throws IOException {
		User user = userRepository.findById(id).orElseThrow();
		user.setName(name);
		user.setSurname(surname);
		if (fileImage.getSize() != 0 ) {
		user.setImage(null);
		user.setImageFile(BlobProxy.generateProxy(fileImage.getInputStream(), fileImage.getSize()));
			}
		userRepository.save(user);
		return "redirect:/user/setting-edit-profile";
	}

	@PostMapping("/user/delete-image")
	public String deleteImage(@RequestParam Long id){
		User user = userRepository.findById(id).orElseThrow();
		user.setImageFile(null);
		user.setImage(null);
		userRepository.save(user);
		return "redirect:/user/setting-edit-profile";
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
