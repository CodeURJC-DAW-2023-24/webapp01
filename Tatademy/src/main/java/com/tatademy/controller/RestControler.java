package com.tatademy.controller;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.tatademy.model.Curso;
import com.tatademy.model.Review;
import com.tatademy.model.User;
import com.tatademy.repository.CursoRepository;
import com.tatademy.repository.ReviewRepository;
import com.tatademy.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RestControler {

	@Autowired
	private CursoRepository cursos;

	// @Autowired
	// private MaterialRepositoy materiales;
	@Autowired
	private ReviewRepository reviews;

	@Autowired
	private UserRepository users;

	// CRUD USERS
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return users.findAll();
	}

	@GetMapping("/user/{id}")
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

	// CRUD Cursos
	@GetMapping("/courses")
	public String getMethodName(Model model) {
		List<String> filters = cursos.findAllCategorias();
		List<String[]> filterPair = new ArrayList<>();
		for (int i = 0; i < filters.size(); ++i) {
			String[] aux = new String[2];
			aux[0] = filters.get(i);
			aux[1] = "";
			filterPair.add(aux);
		}
		model.addAttribute("courses", cursos.findAll());
		model.addAttribute("filters", filterPair);
		return "course-grid";
	}

	@GetMapping("/course-search")
	public String courseSearch(Model model, @RequestParam String name) {
		List<Curso> courses;
		courses = cursos.findByNombreContains(name);
		List<String> filters = cursos.findAllCategorias();
		List<String[]> filterPair = new ArrayList<>();
		for (int i = 0; i < filters.size(); ++i) {
			String[] aux = new String[2];
			aux[0] = filters.get(i);
			aux[1] = "";
			filterPair.add(aux);
		}
		model.addAttribute("filters", filterPair);
		model.addAttribute("courses", courses);
		return "course-grid";
	}

	@GetMapping("/course-filter")
	public String processForm(Model model, HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		List<String> allFilters = cursos.findAllCategorias();
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
			model.addAttribute("courses", cursos.findByCategoriaIn(filters));
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
		model.addAttribute("filters", filterPair);
		return "course-grid";
	}

	@PostMapping("/admin/{id}/new/course")
	public ResponseEntity<Curso> postMethodName(@PathVariable Integer id, @RequestBody Curso course) {
		User usuario = users.findById(id).orElseThrow();
		usuario.getCourses().add(course);
		users.save(usuario);
		return ResponseEntity.ok(course);
	}

	@PutMapping("/new/course")
	public ResponseEntity<Curso> putMethodName(@RequestBody Curso course) {
		Curso curso = cursos.findById(course.getId()).orElseThrow();
		return ResponseEntity.ok(curso);
	}

	@DeleteMapping("/course/{courseId}")
	public ResponseEntity<Curso> deleteCourse(@PathVariable Integer courseId) {
		Curso curso = cursos.findById(courseId).orElseThrow();
		cursos.deleteById(curso.getId());
		return ResponseEntity.ok(curso);
	}

	// !CRUD Cursos

	// CRUD REVIEWS
	@GetMapping("/reviews")
	public List<Review> getReviews() {
		return reviews.findAll();
	}

	@PostMapping("/course/{idCourse}/new/review/{idUser}")
	public ResponseEntity<Review> postMethodName(@PathVariable Integer idCourse, @PathVariable Integer idUser,
			@RequestBody Review review) {
		User user = users.findById(idUser).orElseThrow();
		Curso course = cursos.findById(idCourse).orElseThrow();

		users.save(user);
		course.getReviews().add(review);
		cursos.save(course);

		return ResponseEntity.ok(review);
	}

	// !CRUD REVIEWS

}
