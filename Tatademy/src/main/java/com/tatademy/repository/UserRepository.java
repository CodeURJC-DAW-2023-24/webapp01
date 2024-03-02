package com.tatademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tatademy.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	boolean existsByEmail(String email);

	@Query("SELECT DISTINCT u FROM User u WHERE u.email = :email")
	Page<User> findByEmail(Pageable pageable, String email);

	Optional<User> findByEmail(String email);

	User findByName(String name);
}
