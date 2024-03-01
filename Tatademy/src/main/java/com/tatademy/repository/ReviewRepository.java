package com.tatademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tatademy.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
