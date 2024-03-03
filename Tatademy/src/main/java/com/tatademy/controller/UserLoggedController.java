package com.tatademy.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tatademy.model.Course;
import com.tatademy.model.Review;
import com.tatademy.model.User;
import com.tatademy.service.CourseService;
import com.tatademy.service.ReviewService;
import com.tatademy.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserLoggedController {

	@Autowired
	private UserService userService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
		Principal principal = request.getUserPrincipal();
		model.addAttribute("name", userService.findNameByEmail(principal.getName()));
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
		User user = userService.findByEmail(principal.getName());
		model.addAttribute("surname", user.getSurname());
		model.addAttribute("id", user.getId());
		model.addAttribute("email", user.getEmail());
		model.addAttribute("userEditProfile", true);
		model.addAttribute("footerWithoutAOS", true);
		return "setting-edit-profile";
	}

	@PostMapping("/user/update-profile")
	public String updateProfile(@RequestParam Long id, @RequestParam("fileImage") MultipartFile fileImage,
			@RequestParam String name, @RequestParam String surname) throws IOException {
		User user = userService.findById(id).orElseThrow();
		user.setName(name);
		user.setSurname(surname);
		if (fileImage.getSize() != 0) {
			user.setImage(null);
			user.setImageFile(BlobProxy.generateProxy(fileImage.getInputStream(), fileImage.getSize()));
		}
		userService.save(user);
		return "redirect:/user/setting-edit-profile";
	}

	@PostMapping("/user/delete-image")
	public String deleteImage(@RequestParam Long id) {
		User user = userService.findById(id).orElseThrow();
		user.setImageFile(null);
		user.setImage(null);
		userService.save(user);
		return "redirect:/user/setting-edit-profile";
	}

	@PostMapping("/user/update-password")
	public String updatePassword(HttpServletRequest request, RedirectAttributes redirectAttrs, String currentpassword,
			String pass1, String pass2) {
		if (pass1.equals(pass2)) {
			User user = userService.findByEmail(request.getUserPrincipal().getName());
			String password = user.getPassword();
			if (passwordEncoder.matches(currentpassword, password)) {
				user.setPassword(passwordEncoder.encode(pass1));
				userService.save(user);
			} else {
				redirectAttrs.addFlashAttribute("error", "La contraseña introducida no coincide con la del usuario");
			}
		} else {
			redirectAttrs.addFlashAttribute("error", "La nueva contraseña no coincide con la confirmación contraseña");
		}
		return "redirect:/user/setting-student-security";
	}

	@GetMapping("/user/setting-student-security")
	public String settingStudentSecurity(Model model, HttpServletRequest request) {
		model.addAttribute("surname", userService.findSurnameByEmail(request.getUserPrincipal().getName()));
		return "setting-student-security";
	}

	@GetMapping("/user/setting-student-delete-profile")
	public String settingStudentDeleteProfile(Model model, HttpServletRequest request) {
		model.addAttribute("surname", userService.findSurnameByEmail(request.getUserPrincipal().getName()));
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

	@GetMapping("/user/my-course-list")
	public String myCourseList(Model model, HttpServletRequest request, HttpSession session) {
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
		coursesList = userService.findByEmail(request.getUserPrincipal().getName()).getCourses();
		if (!coursesList.isEmpty()) {
			for (int i = 0; i < coursesList.size(); ++i) {
				String[] aux = new String[6];
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
				try {
					aux[2] = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(coursesList.get(i)
							.getImageFile().getBytes(1, (int) coursesList.get(i).getImageFile().length()));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				aux[3] = String.valueOf(valoration);
				aux[4] = "";
				aux[5] = String.valueOf(coursesList.get(i).getId());
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
				courseData.put("3", String.format("%.2f", valoration));
				courseData.put("5", aux[5]);
				courseData.put("stars", stars);
				coursesModel.add(courseData);
			}
		}
		model.addAttribute("newest", "selected");
		model.addAttribute("valoration", "");
		model.addAttribute("mostReviewed", "");
		model.addAttribute("search", "");
		model.addAttribute("courses", coursesModel);
		model.addAttribute("filters", filterPair);
		model.addAttribute("delete", false);

		return "my-course-list";
	}

	@GetMapping("/user/my-reviews-list")
	public String myReviewsList(Model model, HttpServletRequest request) {
		User user = userService.findByEmail(request.getUserPrincipal().getName());
		List<Review> allReviews = user.getReviews();

		// Prepare a list to hold processed review data
		List<Map<String, Object>> reviewDataList = new ArrayList<>();

		// Fetch the course image for each review and convert to Base64
		for (Review review : allReviews) {
			Map<String, Object> reviewData = new HashMap<>();
			Blob imageBlob = review.getCourse().getImageFile();
			try {
				byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
				String base64EncodedImage = Base64.getEncoder().encodeToString(imageData);
				reviewData.put("review", review);
				reviewData.put("base64EncodedImage", base64EncodedImage);
				reviewDataList.add(reviewData);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		model.addAttribute("reviews", allReviews);
		model.addAttribute("reviewDataList", reviewDataList);

		return "my-reviews-list";
	}

	@GetMapping("/TatademyStadict")
	public String tatadyStadistic(Model model) {

		// total reviews and stars average
		model.addAttribute("usersEnroll", userService.findUsersEnrolledInAtLeastOneCourse().size());
		List<Review> allReviews = reviewService.findAll();
		Double starAverage = 0.0;
		for (Review review : allReviews) {
			starAverage += review.getStarsValue();
		}
		model.addAttribute("TotalReviews", allReviews.size());
		model.addAttribute("starsAverage", starAverage / (allReviews.size()));

		return "instructor-dashboard";
	}

	@GetMapping("/cursesMonth")
	@ResponseBody
	public Integer[] CursesMonthUntilOct() {
		List<Course> allCourses = courseService.findAll();
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
	public Integer[] ReviewsMonthUntilSept() {
		List<Review> allreviews = reviewService.findAll();
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
