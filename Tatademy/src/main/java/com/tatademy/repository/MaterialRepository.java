package com.tatademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tatademy.model.Material;

public interface MaterialRepository extends JpaRepository<Material, Integer> {

}
