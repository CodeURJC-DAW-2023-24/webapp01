package com.tatademy.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tatademy.model.User;
import com.tatademy.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class DatabaseInitializer {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${security.user}")
	private String userName;

	@Value("${security.encodedPassword}")
	private String encodedPassword;

	@PostConstruct
	public void init() throws IOException, URISyntaxException {
		// Sample users
		User user = new User("user", "user", "user", passwordEncoder.encode("pass"));
		user.setRoles(List.of("USER"));
		userRepository.save(user);
		User admin = new User(userName, userName, userName, encodedPassword);
		admin.setRoles(List.of("USER", "ADMIN"));
		userRepository.save(admin);
	}

}
