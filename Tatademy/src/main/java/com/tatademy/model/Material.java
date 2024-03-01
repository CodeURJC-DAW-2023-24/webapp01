package com.tatademy.model;

import java.sql.Blob;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Material {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String filename;
	@Lob
	private Blob file;

	public Material() {
		super();
	}

	public Material(String filename, Blob file) {
		super();
		this.filename = filename;
		this.file = file;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Blob getFile() {
		return file;
	}

	public void setFile(Blob imagenFile) {
		this.file = imagenFile;
	}

}
