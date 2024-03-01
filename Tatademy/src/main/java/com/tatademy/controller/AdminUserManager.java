package com.tatademy.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Base64;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

@Controller
public class AdminUserManager {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			model.addAttribute("logged", true);
			model.addAttribute("userName", userRepository.findNameByEmail(principal.getName()));
			model.addAttribute("admin", request.isUserInRole("ADMIN"));
			model.addAttribute("user", request.isUserInRole("USER"));
			byte[] imageBlob = userService.findImageByEmail(principal.getName());
			if (imageBlob != null) {
				String base64Image = Base64.getEncoder().encodeToString(imageBlob);
				model.addAttribute("userImage", base64Image);
			}
		} else {
			model.addAttribute("logged", false);
		}
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
	}

	@GetMapping("/admin/users")
	public String getUsers(@RequestParam(defaultValue = "0") int page, Model model) throws SQLException {
		Pageable pageable = PageRequest.of(page, 10);
		Page<User> usersPage = userRepository.findAll(pageable);
		for (int i = 0; i < usersPage.getNumberOfElements(); i++) {
			if (usersPage.getContent().get(i).getImageFile() != null) {
				usersPage.getContent().get(i)
						.setImage("data:image/jpeg;base64,"
								+ Base64.getEncoder().encodeToString(usersPage.getContent().get(i).getImageFile()
										.getBytes(1, (int) usersPage.getContent().get(i).getImageFile().length())));
			}
		}
		model.addAttribute("numUsers", usersPage.getNumberOfElements());
		model.addAttribute("numUsersMax", userRepository.findAll().size());
		model.addAttribute("users", usersPage);
		model.addAttribute("hasNext", usersPage.hasNext());
		model.addAttribute("currentPage", pageable.getPageNumber() + 1);
		model.addAttribute("searched", "");
		return "instructor-edit-profile";
	}

	@PostMapping("/admin/delete")
	public String deleteUser(@RequestParam Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			user.getRoles().clear();
			userRepository.delete(user);
		}
		return "redirect:/admin/users";
	}

	@PostMapping("/admin/update")
	public String userinfo(Model model, @RequestParam Long userId) throws SQLException {
		User user = userRepository.findById(userId).orElseThrow();
		if (user.getImageFile() != null) {
			user.setImage("data:image/jpeg;base64," + Base64.getEncoder()
					.encodeToString(user.getImageFile().getBytes(1, (int) user.getImageFile().length())));
		}
		model.addAttribute("user", user);
		model.addAttribute("adminUsersEdit", true);
		return "edit-other-profiles";
	}

	@PostMapping("/admin/user/deleteImage")
	public String deleteimageProfile(Model model, @RequestParam Long id) {
		User user = userRepository.findById(id).orElseThrow();
		user.setImage(null);
		user.setImageFile(null);
		userRepository.save(user);
		model.addAttribute("user", user);
		return "edit-other-profiles";
	}

	@PostMapping("/admin/user/profile")
	public String postMethodName(@RequestParam Long id, @RequestParam("fileImage") MultipartFile fileImage,
			@RequestParam String name, @RequestParam String surname, @RequestParam String email) throws IOException {
		User user = userRepository.findById(id).orElseThrow();
		user.setName(name);
		user.setSurname(surname);
		user.setImage(null);
		user.setImageFile(BlobProxy.generateProxy(fileImage.getInputStream(), fileImage.getSize()));
		user.setEmail(email);
		userRepository.save(user);
		return "redirect:/admin/users";
	}

	@GetMapping("/admin/search-user")
	public String searchUser(@RequestParam String email, Model model) {
		int page = 0;
		Pageable pageable = PageRequest.of(page, 2);
		Page<User> usersPage = userRepository.findByEmailContains(pageable, email);
		if (email == "") {
			usersPage = userRepository.findAll(pageable);
		}
		model.addAttribute("numUsers", usersPage.getNumberOfElements());
		model.addAttribute("numUsersMax", userRepository.findAll().size());
		model.addAttribute("users", usersPage);
		model.addAttribute("hasNext", usersPage.hasNext());
		model.addAttribute("currentPage", pageable.getPageNumber() + 1);
		model.addAttribute("searched", email);
		return "instructor-edit-profile";
	}

}
