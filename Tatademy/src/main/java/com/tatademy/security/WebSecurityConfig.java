package com.tatademy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	public RepositoryUserDetailsService userDetailService;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authenticationProvider(authenticationProvider());
		http.authorizeHttpRequests(authorize -> authorize
				// STATIC RESOURCES
				.requestMatchers("/assets/**", "/css/**", "/js/**", "/img/**", "/scss/**", "/cdn-cgi/**", "/cloudflare-static/**").permitAll()
				// PUBLIC PAGES
				.requestMatchers("/").permitAll()
				.requestMatchers("/faq").permitAll()
				.requestMatchers("/login").permitAll()
				.requestMatchers("/register").permitAll()
				.requestMatchers("/forgot-password").permitAll()
				.requestMatchers("/signup").permitAll()
				.requestMatchers("/logout").permitAll()
				.requestMatchers("/error").permitAll()
				.requestMatchers("/courses").permitAll()
				.requestMatchers("/course-search/{numPage}").permitAll()
				.requestMatchers("/courses/{numPage}").permitAll()
				.requestMatchers("/course-search").permitAll()
				.requestMatchers("/course-filter").permitAll()
				.requestMatchers("/course-details/**").permitAll()
				// USER PAGES
				.requestMatchers("/user/**").hasAnyRole("USER")
				.requestMatchers("/generate-pdf").hasAnyRole("USER")
				// ADMIN PAGES
				.requestMatchers("/course-search-admin/{numPage}").permitAll()
				.requestMatchers("/courses-panel").hasAnyRole("ADMIN")
				.requestMatchers("/delete-course").hasAnyRole("ADMIN")
				.requestMatchers("/new/course").hasAnyRole("ADMIN")
				.requestMatchers("/delete/{id}").hasAnyRole("ADMIN")
				.requestMatchers("/admin/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated())
		
				// LOGIN
				.formLogin(formLogin -> formLogin
						.loginPage("/login")
						.failureUrl("/login")
						.defaultSuccessUrl("/")
						.permitAll())
				// LOGOUT
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/")
						.permitAll());

		return http.build();
	}

}