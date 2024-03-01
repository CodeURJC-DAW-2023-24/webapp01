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
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authenticationProvider(authenticationProvider());
		http.authorizeHttpRequests(authorize -> authorize
				// STATIC RESOURCES
				.requestMatchers("/assets/**", "/css/**", "/js/**", "/img/**", "/scss/**", "/cdn-cgi/**","/cloudflare-static/**").permitAll()
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
				.requestMatchers("/course-search").permitAll()
				// USER PAGES
				.requestMatchers("/setting-edit-profile").hasAnyRole("USER")
				// ADMIN PAGES
				.requestMatchers("/new/course").hasAnyRole("ADMIN")
				.requestMatchers("/delete/{id}").hasAnyRole("ADMIN")
				.requestMatchers("/admin/**").hasAnyRole("ADMIN")
				
				.anyRequest().anonymous())
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

		http.csrf(csrf -> csrf.disable());

		return http.build();
	}

}