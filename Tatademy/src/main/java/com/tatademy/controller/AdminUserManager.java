package com.tatademy.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tatademy.model.Course;
import com.tatademy.model.Material;
import com.tatademy.model.User;
import com.tatademy.service.CourseService;
import com.tatademy.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminUserManager {

	@Autowired
	private UserService userService;

	@Autowired
	private CourseService courseService;

	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			model.addAttribute("logged", true);
			model.addAttribute("userName", userService.findNameByEmail(principal.getName()));
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
		Page<User> usersPage = userService.findAll(pageable);
		for (int i = 0; i < usersPage.getNumberOfElements(); i++) {
			if (usersPage.getContent().get(i).getImageFile() != null) {
				usersPage.getContent().get(i)
						.setImage("data:image/jpeg;base64,"
								+ Base64.getEncoder().encodeToString(usersPage.getContent().get(i).getImageFile()
										.getBytes(1, (int) usersPage.getContent().get(i).getImageFile().length())));
			}
		}
		model.addAttribute("numUsers", usersPage.getNumberOfElements() * (1+page));
		model.addAttribute("numUsersMax", userRepository.findAll().size());
		model.addAttribute("users", usersPage);
		model.addAttribute("hasNext", usersPage.hasNext());
		model.addAttribute("currentPage", pageable.getPageNumber() + 1);
		model.addAttribute("searched", "");
		model.addAttribute("isNotFirst", page!=0);
		model.addAttribute("previousPage", pageable.getPageNumber() - 1);
		model.addAttribute("isTherePages", usersPage.getTotalPages() != 1);
		return "instructor-edit-profile";
	}

	@PostMapping("/admin/delete")
	public String deleteUser(@RequestParam Long userId) {
		User user = userService.findById(userId).orElse(null);
		if (user != null) {
			user.getRoles().clear();
			userService.delete(user);
		}
		return "redirect:/admin/users";
	}

	@PostMapping("/admin/update")
	public String userinfo(Model model, @RequestParam Long userId) throws SQLException {
		User user = userService.findById(userId).orElseThrow();
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
		User user = userService.findById(id).orElseThrow();
		user.setImage(null);
		user.setImageFile(null);
		userService.save(user);
		model.addAttribute("user", user);
		return "edit-other-profiles";
	}

	@PostMapping("/admin/user/profile")
	public String postMethodName(@RequestParam Long id, @RequestParam("fileImage") MultipartFile fileImage,
			@RequestParam String name, @RequestParam String surname) throws IOException {
		User user = userService.findById(id).orElseThrow();
		user.setName(name);
		user.setSurname(surname);
		if (fileImage.getSize() != 0) {
			user.setImage(null);
			user.setImageFile(BlobProxy.generateProxy(fileImage.getInputStream(), fileImage.getSize()));
		}
		userService.save(user);
		return "redirect:/admin/users";
	}

	@GetMapping("/admin/search-user")
	public String searchUser(@RequestParam String email, Model model) {
		int page = 0;
		Pageable pageable = PageRequest.of(page, 2);
		Page<User> usersPage = userService.findByEmailContains(pageable, email);
		if (email == "") {
			usersPage = userService.findAll(pageable);
		}
		model.addAttribute("numUsers", usersPage.getNumberOfElements());
		model.addAttribute("numUsersMax", userService.findAll().size());
		model.addAttribute("users", usersPage);
		model.addAttribute("hasNext", usersPage.hasNext());
		model.addAttribute("currentPage", pageable.getPageNumber() + 1);
		model.addAttribute("searched", email);
		return "instructor-edit-profile";
	}

	@GetMapping("/admin/edit/course/{id}")
	public String getMethodName(Model model, @PathVariable Long id) {
		Course course = courseService.findById(id).orElseThrow();
		model.addAttribute("idEdit", course.getId());
		model.addAttribute("titleEdit", course.getName());
		// Get Category:
		String category = course.getCategory().replaceAll("[í]", "i");
		category = category.replaceAll("[ó]", "o");
		category = category.replaceAll("[ú]", "u");
		category = category.replaceAll("[á]", "a");
		category = category.replaceAll("[é]", "e");
		category = "is" + category + "Selected";
		model.addAttribute("prueba", category);
		model.addAttribute(category, true);
		model.addAttribute("descriptionInput", course.getDescription());
		model.addAttribute("categoryInput", course.getCategory());
		model.addAttribute("isTherePhotoCourse", course.getImageFile());
		model.addAttribute("isEditingCourse", true);

		return "add-course";
	}

	@PostMapping("/admin/edit/course")
	public String getMethodName(Model model, @RequestParam Long idEdit, @RequestParam String title,
			@RequestParam String subject, @RequestParam String description,
			@RequestParam("fileImage") MultipartFile fileImage,
			@RequestParam("courseContentInputFiles") List<MultipartFile> courseContentInputFiles) throws IOException {
		Course course = courseService.findById(idEdit).orElseThrow();
		course.setName(title);
		course.setCategory(subject);
		course.setDescription(description);
		if (fileImage != null) {
			if (fileImage.getSize() != 0) {
				course.setimageString(fileImage.getOriginalFilename());
				course.setImageFile(BlobProxy.generateProxy(fileImage.getInputStream(), fileImage.getSize()));
			}
		}
		if (courseContentInputFiles != null) {
			if (courseContentInputFiles.size() != 0) {
				for (int i = 0; i < courseContentInputFiles.size(); i++) {
					Material material = new Material();
					material.setFilename(courseContentInputFiles.get(i).getOriginalFilename());
					material.setFile(BlobProxy.generateProxy(courseContentInputFiles.get(i).getInputStream(),
							courseContentInputFiles.get(i).getSize()));
					material.setCourse(course);
					course.getMaterial().add(material);
				}

			}
		}
		courseService.save(course);
		return "redirect:/courses";

	}
}
