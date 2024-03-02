package com.tatademy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tatademy.model.User;
import com.tatademy.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public void save(User user) {
		repository.save(user);
	}

	public void delete(User user) {
		repository.delete(user);
	}

	public void findById(Long id) {
		repository.findById(id);
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

	public byte[] findImageByEmail(String email) {
		return repository.findImageByEmail(email);
	}

	public Page<User> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public List<User> findAll() {
		return repository.findAll();
	}
}
