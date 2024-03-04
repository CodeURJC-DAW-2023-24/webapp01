package com.tatademy.controller;

import java.security.Principal;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.tatademy.model.User;
import com.tatademy.repository.CourseRepository;
import com.tatademy.repository.UserRepository;
import com.tatademy.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RestControler {

	@Autowired
	private CourseRepository cursos;

	// @Autowired
	// private MaterialRepositoy materiales;
	
	// @Autowired
	// private ReviewRepository reviews;

	@Autowired
	private UserRepository users;
	
	@Autowired
	private UserService userService;

	// CRUD USERS
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return users.findAll();
	}
	
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
		} else {
			model.addAttribute("logged", false);
		}
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
	}

	/*@GetMapping("/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable int id) {
		try {
			User user = users.getReferenceById(id);
			return ResponseEntity.ok(user);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/new/user")
	public ResponseEntity<User> postUser(@RequestBody User user) {
		this.users.save(user);
		URI location = fromCurrentRequest().path("/user/{id}").buildAndExpand(users.getReferenceById(user.getId()))
				.toUri();
		return ResponseEntity.created(location).body(user);
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<User> putUser(@PathVariable Integer id, @RequestBody User user) {
		try {
			// User oldUser = users.getReferenceById(id);
			user.setId(id);
			users.save(user);
			return ResponseEntity.ok(user);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<User> DeleteUser(@PathVariable Integer id) {
		try {
			User user = users.getReferenceById(id);
			users.deleteById(id);
			// Esta línea emite error y no entiendo porqué, pero funciona bien
			return ResponseEntity.ok(user);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	// ! CRUD USERS
*/
	// CRUD Cursos
	/*@GetMapping("/courses")
	public String getMethodName(Model model) {
		List<String> filters = cursos.findAllCategories();
		List<String[]> filterPair = new ArrayList<>();
		for (int i = 0; i < filters.size(); ++i) {
			String[] aux = new String[2];
			aux[0] = filters.get(i);
			aux[1] = "";
			filterPair.add(aux);
		}
		model.addAttribute("coursesHeader", true);
		model.addAttribute("courses", cursos.findAll());
		model.addAttribute("filters", filterPair);
		return "course-grid";
	}

	@GetMapping("/course-search")
	public String courseSearch(Model model, @RequestParam String name) {
		List<Course> courses;
		courses = cursos.findByNameContains(name);
		List<String> filters = cursos.findAllCategories();
		List<String[]> filterPair = new ArrayList<>();
		for (int i = 0; i < filters.size(); ++i) {
			String[] aux = new String[2];
			aux[0] = filters.get(i);
			aux[1] = "";
			filterPair.add(aux);
		}
		model.addAttribute("coursesHeader", true);
		model.addAttribute("filters", filterPair);
		model.addAttribute("courses", courses);
		return "course-grid";
	}

	@GetMapping("/course-filter")
	public String processForm(Model model, HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		List<String> allFilters = cursos.findAllCategories();
		List<String> filters = new ArrayList<>();
		List<String> filtersMode = new ArrayList<>();
		List<String> filtersName = new ArrayList<>();
		List<String[]> filterPair = new ArrayList<>();
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
			model.addAttribute("courses", cursos.findByCategoriyIn(filters));
		} else {
			model.addAttribute("courses", cursos.findAll());
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
		model.addAttribute("coursesHeader", true);
		model.addAttribute("filters", filterPair);
		return "course-grid";
	}*/

	/*@PostMapping("/admin/{id}/new/course")
	public ResponseEntity<Course> postMethodName(@PathVariable Integer id, @RequestBody Course course) {
		User usuario = users.findById(id).orElseThrow();
		usuario.getCourses().add(course);
		users.save(usuario);
		return ResponseEntity.ok(course);
	}

	@PutMapping("/new/course")
	public ResponseEntity<Course> putMethodName(@RequestBody Course course) {
		Course curso = cursos.findById(course.getId()).orElseThrow();
		return ResponseEntity.ok(curso);
	}

	@DeleteMapping("/course/{courseId}")
	public ResponseEntity<Course> deleteCourse(@PathVariable Integer courseId) {
		Course curso = cursos.findById(courseId).orElseThrow();
		cursos.deleteById(curso.getId());
		return ResponseEntity.ok(curso);
	}*/
}
	// !CRUD Cursos

	// CRUD REVIEWS
	/*@GetMapping("/reviews")
	public List<Review> getReviews() {
		return reviews.findAll();
	}

	@PostMapping("/course/{idCourse}/new/review/{idUser}")
	public ResponseEntity<Review> postMethodName(@PathVariable Integer idCourse, @PathVariable Integer idUser,
			@RequestBody Review review) {
		User user = users.findById(idUser).orElseThrow();
		Course course = cursos.findById(idCourse).orElseThrow();

		users.save(user);
		course.getReviews().add(review);
		cursos.save(course);

		return ResponseEntity.ok(review);
	}

	// !CRUD REVIEWS

}
*/