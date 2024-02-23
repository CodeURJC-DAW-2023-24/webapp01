package com.tatademy.service;

import org.springframework.beans.factory.annotation.Autowired;
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

	public boolean existsByEmail(String email) {
		return repository.existsByEmail(email);
	}
}
