package com.tatademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tatademy.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Integer> {

}
