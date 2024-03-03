package com.tatademy.controller;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.rowset.serial.SerialBlob;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.tatademy.model.Course;
import com.tatademy.model.Material;
import com.tatademy.model.Review;
import com.tatademy.model.User;

import com.tatademy.repository.CourseRepository;
import com.tatademy.repository.MaterialRepository;
import com.tatademy.repository.ReviewRepository;
import com.tatademy.repository.UserRepository;
import com.tatademy.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CourseController {

	@Autowired
	private CourseRepository courses;

	@Autowired
	private MaterialRepository materialRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository users;

	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			model.addAttribute("logged", true);
			model.addAttribute("userName", userService.findNameByEmail(principal.getName()));
			model.addAttribute("adminNewCourse", request.isUserInRole("ADMIN"));
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
		return "redirect:/courses";
	}
	
	@GetMapping("/courses-panel")
	public String coursesPanel(Model model) throws SQLException {
		List<String> filters = courses.findAllCategories();
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
		coursesList = courses.findAll();
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
			aux[2] = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(coursesList.get(i).getImageFile().getBytes(1, (int) coursesList.get(i).getImageFile().length()));
			aux[3] = coursesList.get(i).getId().toString();
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
		return "course-grid";
	}

	@GetMapping("/courses")
	public String courses(Model model) throws SQLException {
		List<String> filters = courses.findAllCategories();
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
		coursesList = courses.findAll();
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
			aux[2] = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(coursesList.get(i).getImageFile().getBytes(1, (int) coursesList.get(i).getImageFile().length()));
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
		    courseData.put("3", String.valueOf(valoration));
			courseData.put("5", aux[5]);
		    courseData.put("stars", stars);
		    coursesModel.add(courseData);
		}
		model.addAttribute("newest", "selected");
		model.addAttribute("valoration", "");
		model.addAttribute("mostReviewed", "");
		model.addAttribute("search", "");
		model.addAttribute("courses", coursesModel);
		model.addAttribute("filters", filterPair);
		model.addAttribute("delete", false);
		
		return "course-grid";
	}

	
	@GetMapping("/course-search")
	public String courseSearch(Model model, @RequestParam String name, @RequestParam String sellist1) throws SQLException {
		List<Course> coursesList = new ArrayList<>();
		List<String> filters = courses.findAllCategories();
		List<String[]> filterPair = new ArrayList<>();
		List<String[]> courseInfo = new ArrayList<>();
		List<Map<String, Object>> coursesModel = new ArrayList<>();
		Double valoration = 0.0;
		String newest, valorationtxt, mostReviewed;
		for (int i = 0; i < filters.size(); ++i) {
			String[] aux = new String[2];
			aux[0] = filters.get(i);
			aux[1] = "";
			filterPair.add(aux);
		}
		coursesList = courses.findByNameContains(name);
		switch(sellist1) {
		case "Valoración":
			newest = "";
			valorationtxt = "selected";
			mostReviewed = "";
			Collections.sort(coursesList, new Comparator<Course>() {
			    public int compare(Course c1, Course c2) {
			        Double val1 = 0.0;
			        Double val2 = 0.0;
			        for (int i = 0; i < c1.getReviews().size(); ++i) {
			        	val1 += c1.getReviews().get(i).getStarsValue();
			        }
			        val1 = val1 / c1.getReviews().size();
			        for (int j = 0; j < c2.getReviews().size(); ++j) {
			        	val2 += c2.getReviews().get(j).getStarsValue();
			        }
			        val2 = val2 / c2.getReviews().size();
			        if (val1 > val2) {
			            return -1;
			        } else if (val1 < val2) {
			            return 1;
			        } else {
			            return 0;
			        }
			    }
			});
			break;
		case "Más veces valorado":
			newest = "";
			valorationtxt = "";
			mostReviewed = "selected";
			Collections.sort(coursesList, new Comparator<Course>() {
			    public int compare(Course c1, Course c2) {
			        int size1 = c1.getReviews().size();
			        int size2 = c2.getReviews().size();

			        if (size1 < size2) {
			            return -1;
			        } else if (size1 > size2) {
			            return 1;
			        } else {
			            return 0;
			        }
			    }
			});
			break;
		default:
			newest = "selected";
			valorationtxt = "";
			mostReviewed = "";
			Collections.sort(coursesList, new Comparator<Course>() {
				public int compare(Course c1, Course c2) {
					return c1.getCreationDate().compareTo(c2.getCreationDate());
				}
			});
			break;
		}
		
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
			aux[2] = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(coursesList.get(i).getImageFile().getBytes(1, (int) coursesList.get(i).getImageFile().length()));
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
		    courseData.put("3", String.valueOf(valoration));
		    courseData.put("stars", stars);
			courseData.put("5", aux[5]);
		    coursesModel.add(courseData);
		}
		switch(sellist1) {
		case "Publicado recientemente":
			newest = "selected";
			valorationtxt = "";
			mostReviewed = "";
			break;
		case "Valoración":
			newest = "";
			valorationtxt = "selected";
			mostReviewed = "";
			break;
		case "Más veces valorado":
			newest = "";
			valorationtxt = "";
			mostReviewed = "selected";
			break;
		default:
			newest = "selected";
			valorationtxt = "";
			mostReviewed = "";
			break;
		}
		model.addAttribute("newest", newest);
		model.addAttribute("valoration", valorationtxt);
		model.addAttribute("mostReviewed", mostReviewed);
		model.addAttribute("search", name);
		model.addAttribute("filters", filterPair);
		model.addAttribute("courses", coursesModel);
		model.addAttribute("delete", false);
		return "course-grid";
	}

	@GetMapping("/course-filter")
	public String processForm(Model model, HttpServletRequest request) throws SQLException {
		Map<String, String[]> paramMap = request.getParameterMap();
		List<String> allFilters = courses.findAllCategories();
		List<String> filters = new ArrayList<>();
		List<String> filtersMode = new ArrayList<>();
		List<String> filtersName = new ArrayList<>();
		List<String[]> filterPair = new ArrayList<>();
		List<Course> coursesList = new ArrayList<>();
		List<String[]> courseInfo = new ArrayList<>();
		List<Map<String, Object>> coursesModel = new ArrayList<>();
		Double valoration = 0.0;
		if (!paramMap.isEmpty()) {
			for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
				String paramName = entry.getKey();
				String[] paramValues = entry.getValue();
				filtersName.add(paramName);
				for (String paramValue : paramValues) {
					filtersMode.add(paramValue);
					if (paramValue.equals("on")) {
						filters.add(paramName);
					}
				}
			}
			coursesList = courses.findByCategoriyIn(filters);
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
				aux[2] = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(coursesList.get(i).getImageFile().getBytes(1, (int) coursesList.get(i).getImageFile().length()));
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
			    courseData.put("3", String.valueOf(valoration));
			    courseData.put("stars", stars);
			    courseData.put("5", aux[5]);
			    coursesModel.add(courseData);
			}
			model.addAttribute("courses", coursesModel);
		} else {
			coursesList = courses.findAll();
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
				aux[2] = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(coursesList.get(i).getImageFile().getBytes(1, (int) coursesList.get(i).getImageFile().length()));
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
			    courseData.put("3", String.valueOf(valoration));
			    courseData.put("stars", stars);
				courseData.put("5", aux[5]);
			    coursesModel.add(courseData);
			}
			model.addAttribute("courses", coursesModel);
		}
		for (int i = 0; i < allFilters.size(); ++i) {
			String[] aux = new String[2];
			aux[0] = allFilters.get(i);
			if (filters.contains(allFilters.get(i))) {
				aux[1] = "checked";
			} else {
				aux[1] = "";
			}
			filterPair.add(aux);
		}
		model.addAttribute("newest", "selected");
		model.addAttribute("valoration", "");
		model.addAttribute("mostReviewed", "");
		model.addAttribute("search", "");
		model.addAttribute("filters", filterPair);
		model.addAttribute("delete", false);
		return "course-grid";
	}
		
		@GetMapping("/delete-course")
		public String deletecourse(Model model, @RequestParam String id) throws SQLException {
			courses.deleteById(Long.parseLong(id));
			List<String> filters = courses.findAllCategories();
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
			coursesList = courses.findAll();
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
				aux[2] = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(coursesList.get(i).getImageFile().getBytes(1, (int) coursesList.get(i).getImageFile().length()));
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
			    courseData.put("3", String.valueOf(valoration));
			    courseData.put("stars", stars);
				courseData.put("5", aux[5]);
			    coursesModel.add(courseData);
			}
			model.addAttribute("newest", "selected");
			model.addAttribute("valoration", "");
			model.addAttribute("mostReviewed", "");
			model.addAttribute("search", "");
			model.addAttribute("courses", coursesModel);
			model.addAttribute("filters", filterPair);
			model.addAttribute("delete", false);
			return "redirect:/courses-panel";
	}

	private boolean isJoined(Course course, User user){
		return user.getCourses().contains(course);
	}


@GetMapping("/course-details/{courseName}")
	public String courseDetails(Model model, HttpServletRequest request,@PathVariable Long courseName) throws SQLException{
		Principal principal = request.getUserPrincipal();
		User user = userService.findByEmail(principal.getName());
		Course course = courses.findById(courseName).orElseThrow();
		model.addAttribute("name", principal);
		model.addAttribute("joined", isJoined(course, user));
		model.addAttribute("courseName", courseName);
		model.addAttribute("courseDescription", course.getDescription());
		model.addAttribute("courseCategory", course.getCategory());
		model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));


		// Materials
		List<Material> materialsFromCourse = courses.findMaterialsByIdCourse(course.getId());
		model.addAttribute("materialsFromCourse", materialsFromCourse);		

		//INFORMATION RELATED TO REVIEWS
		List<Review> reviewsFromCourse = reviewRepository.findReviewsByIdCourse(course.getId());
		model.addAttribute("reviewsFromCourse", reviewsFromCourse);

		Integer numReviews =  reviewsFromCourse.size();
		model.addAttribute("numReviews", numReviews);
		Integer starAverage = 0;

		for (Review review : reviewsFromCourse) {
			starAverage = review.getStarsValue();
		}	
		model.addAttribute("starAverage", starAverage/numReviews);

		//ABOUT THE COURSE
		List<User> studentsEnroll = userRepository.findAllUserEnrollByIdCourse(course.getId());
		Integer numStudentsEnroll = studentsEnroll.size();
		model.addAttribute("numStudentsEnroll", numStudentsEnroll);
		model.addAttribute("numMaterialsCourse", materialsFromCourse.size());



		//-----------------------------------------


		// ALGORITHM
		List<Map<String, Object>> coursesModelAlgorithm = new ArrayList<>();
		List<String[]> courseInfoAlgorithm = new ArrayList<>();
		Double valorationAlgorithm = 0.0;
		List<User> coursesAlgorithm = users.findAllUsersContainingCourseId(courseName);
		List<Course> coursesFinalAlgorithm = users.findTop5CoursesByFrequency(coursesAlgorithm);
		
		if (coursesFinalAlgorithm.size() != 0 ) {
		coursesFinalAlgorithm.remove(0);
		for (int i = 0; i < coursesFinalAlgorithm.size(); ++i) {
			String[] aux = new String[6];
			List<Review> reviews = new ArrayList<>();
			reviews = coursesFinalAlgorithm.get(i).getReviews();
			valorationAlgorithm = 0.0;
			if (reviews.size() > 0) {
				for (int j = 0; j < reviews.size(); ++j) {
					valorationAlgorithm += reviews.get(j).getStarsValue();
				}
				valorationAlgorithm = valorationAlgorithm / reviews.size();
			}
			aux[0] = coursesFinalAlgorithm.get(i).getName();
			aux[1] = String.valueOf(reviews.size());
			aux[2] = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(coursesFinalAlgorithm.get(i).getImageFile().getBytes(1, (int) coursesFinalAlgorithm.get(i).getImageFile().length()));
			aux[3] = String.valueOf(valorationAlgorithm);
			aux[4] = "";
			aux[5] = String.valueOf(coursesFinalAlgorithm.get(i).getId());
			courseInfoAlgorithm.add(aux);
		}
		for (String[] aux : courseInfoAlgorithm) {
		    Map<String, Object> courseData = new HashMap<>();
		    valorationAlgorithm = Double.parseDouble(aux[3]);
		    List<Map<String, Object>> stars = new ArrayList<>();
		    for (int i = 0; i < 5; i++) {
		        Map<String, Object> star = new HashMap<>();
		        star.put("filled", i < valorationAlgorithm);
		        stars.add(star);
		    }
		    courseData.put("0", aux[0]);
		    courseData.put("1", aux[1]);
		    courseData.put("2", aux[2]);
		    courseData.put("3", String.valueOf(valorationAlgorithm));
			courseData.put("5", aux[5]);
		    courseData.put("stars", stars);
		    coursesModelAlgorithm.add(courseData);
		}
		model.addAttribute("isRecommendedCoursesAvailable", true);
		model.addAttribute("courses", coursesModelAlgorithm);
	} else {
		model.addAttribute("isRecommendedCoursesAvailable", false);
	}
		return "course-details";
	}

	@GetMapping("/joinCourse/{courseName}")
	public String courseInscription(Model model, HttpServletRequest request, @PathVariable String courseName){
		Principal principal = request.getUserPrincipal();
		User user = userService.findByEmail(principal.getName());
		List<Course> currentCurses = user.getCourses();
		currentCurses.add(courses.findByName(courseName));
		user.setCourses(currentCurses);
		userService.save(user);
		//model.addAttribute("joined", true);
		return "redirect:/";
	}


	@GetMapping("/saveReview/{courseName}")
	public String saveReview(Model model, @PathVariable String courseName, HttpServletRequest request, @RequestParam String userNameReview, @RequestParam String userSurnameReview, @RequestParam int rateReview, @RequestParam String descriptionReview) {
    // Ask for user:
    Principal principal = request.getUserPrincipal();
    User user = userService.findByEmail(principal.getName());
    // Create the new review:
    Review newReview = new Review();
    newReview.setStarsValue(rateReview);
    newReview.setDescription(descriptionReview);
	newReview.setCourse(courses.findByName(courseName));
    newReview.setUser(user);
    // Save the review
	reviewRepository.save(newReview);
    return "redirect:/";
	}
	@GetMapping("/generate-pdf")
    public String generatePdf( @RequestParam long id,  HttpServletRequest request, HttpServletResponse response) {
        Principal principal = request.getUserPrincipal();
		User Iam = userService.findByEmail(principal.getName());
		if (Iam.getCourses().contains(courses.findById(id).orElseThrow())) {
		try {
            // Set content type and headers for PDF response
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Certificado.pdf");

            // Create a new PDF document
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());

            // Open the document
            document.open();
			Paragraph blank = new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA,Font.DEFAULTSIZE,Font.NORMAL));
			blank.setAlignment(Paragraph.ALIGN_CENTER);
            // Add content to the PDF
			Paragraph title = new Paragraph("¡FELICIDADES!", FontFactory.getFont(FontFactory.HELVETICA,20,Font.BOLD));
			title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
			Paragraph desc = new Paragraph("Has conseguido el diploma de", FontFactory.getFont(FontFactory.HELVETICA,Font.DEFAULTSIZE,Font.NORMAL));
			desc.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(desc);
			Paragraph subject = new Paragraph(courses.findById(id).orElseThrow().getName(), FontFactory.getFont(FontFactory.HELVETICA,20,Font.BOLD));
			subject.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(subject);
			Paragraph desc2 = new Paragraph("Por haber completados todo el contenido del curso", FontFactory.getFont(FontFactory.HELVETICA,Font.DEFAULTSIZE,Font.NORMAL));
			desc2.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(desc2);

			document.add(blank);
			document.add(blank);
			document.add(blank);
			document.add(blank);
			document.add(blank);
			Paragraph desc3 = new Paragraph("Este diploma se entrega a:", FontFactory.getFont(FontFactory.HELVETICA,Font.DEFAULTSIZE,Font.NORMAL));
			desc2.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(desc3);	
			
			Paragraph desc4 = new Paragraph(Iam.getName() + " "+ Iam.getSurname(), FontFactory.getFont(FontFactory.HELVETICA,Font.DEFAULTSIZE,Font.NORMAL));
			desc2.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(desc4);

			Paragraph brandName = new Paragraph("Tatademy", FontFactory.getFont(FontFactory.TIMES_ROMAN,40,Font.BOLD));
			brandName.setAlignment(Paragraph.ALIGN_RIGHT);
			document.add(brandName);


				// Close the document
				document.close();

				// Flush the response
				response.flushBuffer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
        return "redirect:/course-details/"+id ; // Return null to prevent view resolution
		
    }

	


}

