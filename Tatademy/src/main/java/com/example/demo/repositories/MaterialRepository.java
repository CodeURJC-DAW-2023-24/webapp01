package com.example.demo.repositories;


import com.example.demo.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material,Integer> {

}
