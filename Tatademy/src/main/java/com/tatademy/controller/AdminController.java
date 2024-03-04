package com.tatademy.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tatademy.model.Course;
import com.tatademy.model.Material;
import com.tatademy.model.Review;
import com.tatademy.model.User;
import com.tatademy.service.CourseService;
import com.tatademy.service.MaterialService;
import com.tatademy.service.ReviewService;
import com.tatademy.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private MaterialService materialService;

	@Autowired
	private CourseService courseService;

	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			model.addAttribute("logged", true);
			model.addAttribute("adminArea", true);
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

	@GetMapping("/admin/all/users")
	public String usersShow(Model model) throws SQLException {
		model.addAttribute("adminUsersList", true);
		model.addAttribute("searched", "");
		Pageable pageable = PageRequest.of(0, 10);
		Page<User> usersPage = userService.findAll(pageable);
		for (int i = 0; i < usersPage.getNumberOfElements(); i++) {
			if (usersPage.getContent().get(i).getImageFile() != null) {
				usersPage.getContent().get(i)
						.setImage("data:image/jpeg;base64,"
								+ Base64.getEncoder().encodeToString(usersPage.getContent().get(i).getImageFile()
										.getBytes(1, (int) usersPage.getContent().get(i).getImageFile().length())));
			}
		}

		model.addAttribute("users", usersPage);
		return "instructor-edit-profile";
	}

	@GetMapping("/admin/users")
	public String getUsers(@RequestParam(name = "page", defaultValue = "0") int page, Model model) throws SQLException {
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

		model.addAttribute("users", usersPage);
		return "usersList";
	}

	@PostMapping("/admin/delete")
	public String deleteUser(@RequestParam Long userId) {
		User user = userService.findById(userId).orElse(null);
		if (user != null) {
			user.getRoles().clear();
			userService.delete(user);
		}
		return "redirect:/admin/all/users";
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
		return "redirect:/admin/all/users";
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

	@GetMapping("/admin/delete-course")
	public String deletecourse(Model model, @RequestParam Long id) throws SQLException {
		Course course = courseService.findById(id).orElseThrow();

		// Dissociate reviews from the course
		for (Review review : course.getReviews()) {
			review.setCourse(null);
			reviewService.save(review);
		}
		course.getReviews().clear();

		// Remove course from associated users
		for (User user : course.getUsers()) {
			user.getCourses().remove(course);
			userService.save(user); //
		}
		course.getUsers().clear();

		userService.deleteByCourseId(id);
		courseService.deleteById(id);
		return "redirect:/courses-panel";
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
		return "redirect:/courses-panel";
	}

	@GetMapping("/admin/new/course")
	public String getMethodName(Model model) {
		model.addAttribute("adminNewCourset", true);
		return "add-course";
	}

	@PostMapping("/admin/new/course")
	public String postMethodName(@RequestParam String title, @RequestParam String subject,
			@RequestParam String description, @RequestParam("fileImage") MultipartFile fileImage,
			@RequestParam("courseContentInputFiles") List<MultipartFile> courseContentInputFiles) throws IOException {
		Course course = new Course(title, subject, description);
		course.setimageString(fileImage.getOriginalFilename());
		course.setImageFile(BlobProxy.generateProxy(fileImage.getInputStream(), fileImage.getSize()));
		courseService.save(course);
		for (int i = 0; i < courseContentInputFiles.size(); i++) {
			Material material = new Material();
			material.setFilename(courseContentInputFiles.get(i).getOriginalFilename());
			material.setFile(BlobProxy.generateProxy(courseContentInputFiles.get(i).getInputStream(),
					courseContentInputFiles.get(i).getSize()));
			material.setCourse(course);
			materialService.save(material);
			course.getMaterial().add(material);
		}
		return "redirect:/courses";
	}

	@GetMapping("/courses-panel")
	public String coursesPanel(Model model) throws SQLException {
		List<String> filters = courseService.findAllCategories();
		List<Course> coursesList = new ArrayList<>();
		List<String[]> filterPair = new ArrayList<>();
		List<String[]> courseInfo = new ArrayList<>();
		List<Map<String, Object>> coursesModel = new ArrayList<>();
		Double valoration = 0.0;
		for (int i = 0; i < filters.size(); ++i) {
			String[] aux = new String[2];
			aux[0] = filters.get(i);
			aux[1] = "";
			filterPair.add(aux);
		}
		coursesList = courseService.findAll();
		for (int i = 0; i < coursesList.size(); ++i) {
			String[] aux = new String[5];
			List<Review> reviews = new ArrayList<>();
			reviews = coursesList.get(i).getReviews();
			valoration = 0.0;
			if (reviews.size() > 0) {
				for (int j = 0; j < reviews.size(); ++j) {
					valoration += reviews.get(j).getStarsValue();
				}
				valoration = valoration / reviews.size();
			}
			aux[0] = coursesList.get(i).getName();
			aux[1] = String.valueOf(reviews.size());
			aux[2] = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(
					coursesList.get(i).getImageFile().getBytes(1, (int) coursesList.get(i).getImageFile().length()));
			aux[3] = String.valueOf(valoration);
			aux[4] = String.valueOf(coursesList.get(i).getId());
			courseInfo.add(aux);
		}
		for (String[] aux : courseInfo) {
			Map<String, Object> courseData = new HashMap<>();
			valoration = Double.parseDouble(aux[3]);
			List<Map<String, Object>> stars = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				Map<String, Object> star = new HashMap<>();
				star.put("filled", i < valoration);
				stars.add(star);
			}
			courseData.put("0", aux[0]);
			courseData.put("1", aux[1]);
			courseData.put("2", aux[2]);
			courseData.put("3", String.valueOf(valoration));
			courseData.put("stars", stars);
			courseData.put("id", aux[3]);
			courseData.put("5", aux[4]);
			coursesModel.add(courseData);
		}
		model.addAttribute("newest", "selected");
		model.addAttribute("valoration", "");
		model.addAttribute("mostReviewed", "");
		model.addAttribute("search", "");
		model.addAttribute("courses", coursesModel);
		model.addAttribute("filters", filterPair);
		model.addAttribute("delete", true);
		model.addAttribute("headerBorder", true);
		return "course-grid";
	}

	@GetMapping("/admin/dashboard")
	public String tatadyStadistic(Model model, HttpServletRequest request) {
		List<Course> allCourses;
		List<Review> allReviews;
		if (!request.isUserInRole("ADMIN")) {
			allCourses = userService.findByEmail(request.getUserPrincipal().getName()).getCourses();
			allReviews = userService.findByEmail(request.getUserPrincipal().getName()).getReviews();
		} else {
			allCourses = courseService.findAll();
			allReviews = reviewService.findAll();
		}
		if (!allCourses.isEmpty()) {
			model.addAttribute("coursesEnroll", allCourses.size());
		} else {
			model.addAttribute("coursesEnroll", 0);
		}
		if (!allReviews.isEmpty()) {
			Double starAverage = 0.0;
			for (Review review : allReviews) {
				starAverage += review.getStarsValue();
			}
			model.addAttribute("TotalReviews", allReviews.size());
			model.addAttribute("starsAverage", String.format("%.2f", starAverage / (allReviews.size())));
		} else {
			model.addAttribute("TotalReviews", 0);
			model.addAttribute("starsAverage", 0);
		}
		model.addAttribute("name", userService.findNameByEmail(request.getUserPrincipal().getName()));
		model.addAttribute("surname", userService.findSurnameByEmail(request.getUserPrincipal().getName()));
		model.addAttribute("footerWithoutAOS", true);
		model.addAttribute("chartsJS", true);
		return "instructor-dashboard";
	}

	@GetMapping("/cursesMonth")
	@ResponseBody
	public Integer[] CursesMonthUntilOct(HttpServletRequest request) {
		List<Course> allCourses;
		if (!request.isUserInRole("ADMIN")) {
			allCourses = userService.findByEmail(request.getUserPrincipal().getName()).getCourses();
		} else {
			allCourses = courseService.findAll();
		}

		Integer[] coursesByMonth = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		for (Course course : allCourses) {
			Calendar creationDate = course.getCreationDate();
			int month = creationDate.get(Calendar.MONTH);

			if (month >= 0 && month <= 9) {
				coursesByMonth[month]++;
			}
		}
		for (int i = 0; i < coursesByMonth.length; i++) {
			int cantidadCursos = coursesByMonth[i];
			System.out.println("/cursesMonth  -> Month " + (i + 1) + ": " + cantidadCursos + " courses created");
		}
		return coursesByMonth;
	}

	@GetMapping("/reviewsMonth")
	@ResponseBody
	public Integer[] ReviewsMonthUntilSept(HttpServletRequest request) {
		List<Review> allreviews;
		if (!request.isUserInRole("ADMIN")) {
			allreviews = userService.findByEmail(request.getUserPrincipal().getName()).getReviews();
		} else {
			allreviews = reviewService.findAll();
		}
		Integer[] reviewsByMonth = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		for (Review review : allreviews) {
			Calendar creationDate = review.getCreationDate();
			int month = creationDate.get(Calendar.MONTH);
			if (month >= 0 && month <= 8) {
				reviewsByMonth[month]++;
			}
		}
		for (int i = 0; i < reviewsByMonth.length; i++) {
			int cantidadReview = reviewsByMonth[i];
			System.out.println("/reviewsMonth  -> Month " + (i + 1) + ": " + cantidadReview + " reviews created");
		}
		return reviewsByMonth;
	}

}
