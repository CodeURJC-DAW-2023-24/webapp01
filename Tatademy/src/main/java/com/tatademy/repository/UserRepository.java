package com.tatademy.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tatademy.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	boolean existsByEmail(String email);

	@Query("SELECT DISTINCT u FROM User u WHERE u.email = :email")
	Page<User> findByEmail(Pageable pageable, String email);

	Optional<User> findByEmail(String email);
}
