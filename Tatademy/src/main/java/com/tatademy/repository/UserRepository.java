package com.tatademy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tatademy.model.Course;
import com.tatademy.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);

	Page<User> findByEmailContains(Pageable pageable, String email);
	
	Page<User> findByEmail(Pageable pageable, String email);

	User findByEmail(String email);
	
	@Query("SELECT u.name FROM User u WHERE u.email = :email")
    String findNameByEmail(String email);
	
	@Query("SELECT u.imageFile FROM User u WHERE u.email = :email")
	byte[] findImageByEmail(String email);

	@Query("SELECT u FROM User u JOIN u.courses c where c.id = :courseId ")
	List<User> findAllUsersContainingCourseId(@Param("courseId") Long courseId);

	@Query("SELECT c FROM User u JOIN u.courses c WHERE u IN :users")
	List<Course> findAllCoursesContainingUserId(@Param("users") List<User> users);

	@Query("SELECT c FROM User u JOIN u.courses c WHERE u IN :users GROUP BY c ORDER BY COUNT(c) DESC LIMIT 6")
	List<Course> findTop5CoursesByFrequency(@Param("users") List<User> users);

	@Query("SELECT u FROM User u JOIN u.courses c WHERE c.id = :idCourse")
	List<User> findAllUserEnrollByIdCourse(Long  idCourse);

	@Query("SELECT u FROM User u WHERE EXISTS (SELECT 1 FROM u.courses)")
    List<User> findUsersEnrolledInAtLeastOneCourse();




}
