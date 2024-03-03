package com.tatademy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tatademy.model.Material;
import com.tatademy.repository.MaterialRepository;

@Service
public class MaterialService {

	@Autowired
	private MaterialRepository repository;

	public void save(Material material) {
		repository.save(material);
	}

	public void delete(Material material) {
		repository.delete(material);
	}

	public Optional<Material> findById(Long id) {
		return repository.findById(id);
	}

}
