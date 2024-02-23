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

import com.tatademy.model.Course;
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
	private CursoRepository coursesList;

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
	

	@PostMapping("/admin/{id}/new/course")
	public ResponseEntity<Course> postMethodName(@PathVariable Integer id, @RequestBody Course course) {
		User usuario = users.findById(id).orElseThrow();
		usuario.getCourses().add(course);
		users.save(usuario);
		return ResponseEntity.ok(course);
	}

	@PutMapping("/new/course")
	public ResponseEntity<Course> putMethodName(@RequestBody Course course) {
		Course curso = coursesList.findById(course.getId()).orElseThrow();
		return ResponseEntity.ok(curso);
	}

	@DeleteMapping("/course/{courseId}")
	public ResponseEntity<Course> deleteCourse(@PathVariable Integer courseId) {
		Course curso = coursesList.findById(courseId).orElseThrow();
		coursesList.deleteById(curso.getId());
		return ResponseEntity.ok(curso);
	}

	// CRUD REVIEWS
	@GetMapping("/reviews")
	public List<Review> getReviews() {
		return reviews.findAll();
	}

	@PostMapping("/course/{idCourse}/new/review/{idUser}")
	public ResponseEntity<Review> postMethodName(@PathVariable Integer idCourse, @PathVariable Integer idUser,
			@RequestBody Review review) {
		User user = users.findById(idUser).orElseThrow();
		Course course = coursesList.findById(idCourse).orElseThrow();

		users.save(user);
		course.getReviews().add(review);
		coursesList.save(course);

		return ResponseEntity.ok(review);
	}

	// !CRUD REVIEWS

}
