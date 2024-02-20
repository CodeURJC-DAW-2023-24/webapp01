package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Usuario;

public interface UserRepository extends JpaRepository<Usuario, Integer>{

}
