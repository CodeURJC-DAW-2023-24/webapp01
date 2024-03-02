package com.tatademy.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tatademy.model.User;
import com.tatademy.repository.UserRepository;
import com.tatademy.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			model.addAttribute("logged", true);
			model.addAttribute("admin", request.isUserInRole("ADMIN"));
			model.addAttribute("user", request.isUserInRole("USER"));
			model.addAttribute("userName", principal.getName());
		} else {
			model.addAttribute("logged", false);
		}
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

	@PostMapping("/signup")
	public String signup(User user, RedirectAttributes attributes) {
		if (userService.existsByEmail(user.getEmail())) {
			// attributes.addFlashAttribute("msga", "Error al crear.");
			return "redirect:/register";
		} else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRoles(List.of("USER"));
			userService.save(user);
			return "redirect:/login";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpSession session) {
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		session.invalidate();
		logoutHandler.logout(request, null, null);
		return "redirect:/";
	}

	@GetMapping("/setting-edit-profile")
	public String editProfile() {
		return "setting-edit-profile";
	}


	@GetMapping("/user/profile")
	public String settingEditProfile(Model model, HttpServletRequest request){
		Principal principal = request.getUserPrincipal();
		User user = userRepository.findByName(principal.getName());
		model.addAttribute("user", request.isUserInRole("USER"));
		model.addAttribute("logged", true);
		model.addAttribute("userName", user.getName());
		model.addAttribute("surname", user.getSurname());
		model.addAttribute("image", user.getImage());
		model.addAttribute("image_file", user.getImageFile());
		model.addAttribute("id", user.getId());
		model.addAttribute("email", user.getEmail());
		return "setting-edit-profile";
	}

	@PostMapping("/user/update-profile")
	public String editInfo(@RequestParam Long id, @RequestParam("fileImage") MultipartFile fileImage,
			@RequestParam String name, @RequestParam String surname, @RequestParam String email) throws IOException {
		User user = userRepository.findById(id).orElseThrow();
		user.setName(name);
		user.setSurname(surname);
		user.setImage(null);
		user.setImageFile(BlobProxy.generateProxy(fileImage.getInputStream(), fileImage.getSize()));
		user.setEmail(email);
		userRepository.save(user);
		return "redirect:/user/profile";
	}

	@PostMapping("/user/delete-image")
	public String deleteProfilePicture(Model model, @RequestParam Long id) {
		User user = userRepository.findById(id).orElseThrow();
		user.setImage(null);
		user.setImageFile(null);
		userService.save(user);
		return "edit-other-profiles";
	}

	//ESTO DE ACÁ NO FUNCIONA TODAVÍA, EN ESTO TE QUEDASTE
	@GetMapping("/setting-student-delete-profile")
	public String getMethodName(Model model, HttpServletRequest request) {
			Principal principal = request.getUserPrincipal();
			User user = userRepository.findByName(principal.getName());
			model.addAttribute("user", request.isUserInRole("USER"));
			model.addAttribute("logged", true);
			model.addAttribute("userName", user.getName());
			model.addAttribute("surname", user.getSurname());
			model.addAttribute("image", user.getImage());
			model.addAttribute("image_file", user.getImageFile());
			model.addAttribute("id", user.getId());
			model.addAttribute("email", user.getEmail());
		return "setting-student-delete-profile";
	}

	@PostMapping("/user/delete")
	public String deleteUser(@RequestParam Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			user.getRoles().clear();
			userRepository.delete(user);
		}
		return "redirect:/";
	}
	

}
