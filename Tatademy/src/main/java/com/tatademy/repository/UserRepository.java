package com.tatademy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tatademy.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	boolean existsByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.name = :nameUser")  //AND SURNAME FOR THE FUTURE
	User findUserByName(String nameUser);

	@Query("SELECT u FROM User u WHERE u.name = :nameUser AND u.surname = :surnameUser")  
	User findUserByNameAndSurname(String nameUser, String surnameUser);
	
	
	@Query("SELECT u FROM User u JOIN u.courses c WHERE c.id = :idCourse")
	List<User> findAllUserEnrollByIdCourse(Integer  idCourse);
}
