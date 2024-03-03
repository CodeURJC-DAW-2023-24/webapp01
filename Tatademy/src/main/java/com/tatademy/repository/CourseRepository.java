package com.tatademy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tatademy.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
	List<Course> findByNameContains(String name);

	Course findByName(String name);

	@Query("SELECT DISTINCT c.category FROM Course c")
	List<String> findAllCategories();

	@Query("SELECT c FROM Course c WHERE c.category IN :filters")
	List<Course> findByCategoriyIn(List<String> filters);
}
