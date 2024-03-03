package com.tatademy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tatademy.model.Material;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    @Query("SELECT m FROM Material m WHERE m.id = :idMaterial") 
	Optional<Material> findById(Long idMaterial);
    
}
