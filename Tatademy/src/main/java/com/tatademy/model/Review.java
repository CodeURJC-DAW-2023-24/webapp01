package com.tatademy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int starsValue;
	private String description;
	@ManyToOne
	@JsonIgnore
	private User user;

	public Review() {
		super();
	}

	public Review(int starsValue, String description, User user) {
		super();
		this.starsValue = starsValue;
		this.description = description;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStarsValue() {
		return starsValue;
	}

	public void setStarsValue(int starsValue) {
		this.starsValue = starsValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
