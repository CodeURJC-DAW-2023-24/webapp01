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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String filelocation;
	@Lob
	private Blob file;

	public String getFilelocation() {
		return filelocation;
	}

	public void setFilelocation(String filelocation) {
		this.filelocation = filelocation;
	}

	public Material() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Blob getFile() {
		return file;
	}

	public void setFile(Blob imagenFile) {
		this.file = imagenFile;
	}

	public String getFileName (){
		int lastSlashIndex = filelocation.lastIndexOf("/");
        String fileName = filelocation.substring(lastSlashIndex + 1); // ./from/folder/example.pdf --> example.pdf
		return fileName;
	}

}
