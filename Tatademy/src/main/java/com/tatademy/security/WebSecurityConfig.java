package com.tatademy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
 
    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
         
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(anyRequest ->
            anyRequest.requestMatchers("/").authenticated()
            .anyRequest().permitAll()
            )
            .formLogin(login ->
                login
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .failureUrl("/faq")
                .permitAll()
            )
            .logout(logout -> logout.logoutSuccessUrl("/").permitAll()
        );

        return http.build();
    }  
}