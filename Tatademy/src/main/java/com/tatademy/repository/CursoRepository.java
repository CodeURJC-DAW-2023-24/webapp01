package com.tatademy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tatademy.model.Course;
import com.tatademy.model.Material;
import com.tatademy.model.Review;

public interface CursoRepository extends JpaRepository<Course, Integer> {
	List<Course> findByNameContains(String name);

	@Query("SELECT DISTINCT c.category FROM Course c")
	List<String> findAllCategories();

	@Query("SELECT c FROM Course c WHERE c.category IN :filters")
	List<Course> findByCategoriyIn(List<String> filters);

	@Query("SELECT c.name FROM Course c WHERE c.id = :idCourse") 
	String findNameById(Integer idCourse);

	@Query("SELECT c.category FROM Course c WHERE c.id = :idCourse")
    String findCategoryById(Integer idCourse);

	@Query("SELECT c.description FROM Course c WHERE c.id = :idCourse")
    String findDescriptionById(Integer idCourse);

	@Query("SELECT r FROM Course c JOIN c.reviews r WHERE c.id = :idCourse")
	List<Review> findReviewsByIdCourse(Integer idCourse);

	@Query("SELECT m FROM Course c JOIN c.material m WHERE c.id = :idCourse")
	List<Material> findMaterialsByIdCourse(Integer idCourse);

	
}
