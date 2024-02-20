package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Curso;

public interface CursoRepository extends JpaRepository<Curso,Integer>{

}
