package com.tatademy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.tatademy.model.Course;
import com.tatademy.model.User;
import com.tatademy.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@PersistenceContext
	private EntityManager entityManager;

	public void save(User user) {
		repository.save(user);
	}

	public void delete(User user) {
		repository.delete(user);
	}

	public Optional<User> findById(Long id) {
		return repository.findById(id);
	}

	public boolean existsByEmail(String email) {
		return repository.existsByEmail(email);
	}

	public Page<User> findByEmailContains(Pageable pageable, String email) {
		return repository.findByEmailContains(pageable, email);
	}

	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}

	public String findNameByEmail(String email) {
		return repository.findNameByEmail(email);
	}

	public String findSurnameByEmail(String email) {
		return repository.findSurnameByEmail(email);
	}

	public byte[] findImageByEmail(String email) {
		return repository.findImageByEmail(email);
	}

	public Page<User> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public List<User> findAll() {
		return repository.findAll();
	}

	public List<User> findAllUsersContainingCourseId(@Param("courseId") Long courseId) {
		return repository.findAllUsersContainingCourseId(courseId);
	}

	public List<Course> findAllCoursesContainingUserId(@Param("users") List<User> users) {
		return repository.findAllCoursesContainingUserId(users);
	}

	public List<Course> findTop5CoursesByFrequency(@Param("users") List<User> users) {
		return repository.findTop5CoursesByFrequency(users);
	}

	public List<User> findAllUserEnrollByIdCourse(Long idCourse) {
		return repository.findAllUserEnrollByIdCourse(idCourse);
	}

	public List<User> findUsersEnrolledInAtLeastOneCourse() {
		return repository.findUsersEnrolledInAtLeastOneCourse();
	}

	@Transactional
	public void deleteByCourseId(Long courseId) {
		entityManager.createNativeQuery("DELETE FROM user_courses WHERE courses_id = :courseId")
				.setParameter("courseId", courseId).executeUpdate();
	}
}
