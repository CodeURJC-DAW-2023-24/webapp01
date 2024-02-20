package com.tatademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tatademy.model.Usuario;

public interface UserRepository extends JpaRepository<Usuario, Integer> {

}
