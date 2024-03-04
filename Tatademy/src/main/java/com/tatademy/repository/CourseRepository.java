package com.tatademy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tatademy.model.Course;
import com.tatademy.model.Material;

public interface CourseRepository extends JpaRepository<Course, Long> {
	Page<Course> findByNameContains(String name, Pageable pageable);

	Course findByName(String name);

	Optional<Course> findById(Long id);

	@Query("SELECT DISTINCT c.category FROM Course c")
	List<String> findAllCategories();

	@Query("SELECT c FROM Course c WHERE c.category IN :filters")
	Page<Course> findByCategoriyIn(List<String> filters, Pageable pageable);

	@Query("SELECT c.category FROM Course c WHERE c.id = :idCourse")
	String findCategoryById(Long idCourse);

	@Query("SELECT c.description FROM Course c WHERE c.id = :idCourse")
	String findDescriptionById(Long idCourse);

	@Query("SELECT m FROM Course c JOIN c.material m WHERE c.id = :idCourse")
	List<Material> findMaterialsByIdCourse(Long idCourse);

}
