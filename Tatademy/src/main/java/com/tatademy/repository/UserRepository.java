package com.tatademy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

	User findByName(String name);
}
