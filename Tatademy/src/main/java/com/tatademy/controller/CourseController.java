package com.tatademy.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tatademy.model.Course;
import com.tatademy.model.Material;
import com.tatademy.repository.CourseRepository;
import com.tatademy.repository.MaterialRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CourseController {

	@Autowired
	private CourseRepository courses;

	@Autowired
	private MaterialRepository materialRepository;

	@GetMapping("/new/course")
	public String getMethodName() {
		return "add-course";
	}

	@PostMapping("/new/course")
	public String postMethodName(@RequestParam String title, @RequestParam String subject,
			@RequestParam String description, @RequestParam("fileImage") MultipartFile fileImage,
			@RequestParam("courseContentInputFiles") List<MultipartFile> courseContentInputFiles) throws IOException {
		Course course = new Course(title, subject, description);
		course.setimageString(fileImage.getOriginalFilename());
		course.setImageFile(BlobProxy.generateProxy(fileImage.getInputStream(), fileImage.getSize()));
		courses.save(course);
		for (int i = 0; i < courseContentInputFiles.size(); i++) {
			Material material = new Material();
			material.setFilename(courseContentInputFiles.get(i).getOriginalFilename());
			material.setFile(BlobProxy.generateProxy(courseContentInputFiles.get(i).getInputStream(),
					courseContentInputFiles.get(i).getSize()));
			material.setCourse(course);
			materialRepository.save(material);
			course.getMaterial().add(material);
		}
		return "redirect:/new/course"; // MODIFY THIS AT SOME POINT TO REDIRECT TO COURSES
	}

	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			model.addAttribute("logged", true);
			model.addAttribute("userName", principal.getName());
			model.addAttribute("adminNewCourse", request.isUserInRole("ADMIN"));
			model.addAttribute("admin", request.isUserInRole("ADMIN"));
			model.addAttribute("user", request.isUserInRole("USER"));
		} else {
			model.addAttribute("logged", false);
		}
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
	}

}
