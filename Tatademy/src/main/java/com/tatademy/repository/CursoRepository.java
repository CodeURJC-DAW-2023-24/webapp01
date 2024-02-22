package com.tatademy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tatademy.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
	List<Curso> findByNombreContains(String name);
	@Query("SELECT DISTINCT c.categoria FROM Curso c")
    List<String> findAllCategorias();
	@Query("SELECT c FROM Curso c WHERE c.categoria IN :filters")
    List<Curso> findByCategoriaIn(List<String> filters);
}

