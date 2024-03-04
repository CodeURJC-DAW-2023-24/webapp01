package com.tatademy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tatademy.model.Course;
import com.tatademy.model.Material;
import com.tatademy.repository.CourseRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository repository;

	public void save(Course course) {
		repository.save(course);
	}

	public void delete(Course course) {
		repository.delete(course);
	}

	public Optional<Course> findById(Long id) {
		return repository.findById(id);
	}

	public Page<Course> findByNameContains(String name, Pageable pageable) {
		return repository.findByNameContains(name, pageable);
	}

	public Course findByName(String name) {
		return repository.findByName(name);
	}

	public List<String> findAllCategories() {
		return repository.findAllCategories();
	}

	public Page<Course> findByCategoriyIn(List<String> filters, Pageable pageable) {
		return repository.findByCategoriyIn(filters, pageable);
	}

	public String findCategoryById(Long idCourse) {
		return repository.findCategoryById(idCourse);
	}

	public String findDescriptionById(Long idCourse) {
		return repository.findDescriptionById(idCourse);
	}

	public List<Material> findMaterialsByIdCourse(Long idCourse) {
		return repository.findMaterialsByIdCourse(idCourse);
	}

	public Page<Course> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	public List<Course> findAll() {
		return repository.findAll();
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}
}
