package com.tatademy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tatademy.model.Review;
import com.tatademy.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository repository;

	public void save(Review review) {
		repository.save(review);
	}

	public void delete(Review review) {
		repository.delete(review);
	}

	public Optional<Review> findById(Long id) {
		return repository.findById(id);
	}

	public List<Review> findReviewsByIdCourse(Long idCourse) {
		return repository.findReviewsByIdCourse(idCourse);
	}
	
	public List<Review> findAll() {
		return repository.findAll();
	}


}
