package com.tatademy.model;

import java.sql.Blob;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Usuario {

	private String nombre;
	private String apellidos;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String correo;
	@ManyToMany
	private List<Curso> cursos;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Review> reviews;
	// Zona imagen de perfil
	private String imagen;
	@Lob
	@JsonIgnore
	private Blob imagenFile;

	public Usuario() {

	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Usuario(String nombre, String apellido1, String correo) {
		this.nombre = nombre;
		this.apellidos = apellido1;
		this.correo = correo;
	}

	public Usuario(String nombre, String apellido1, String correo, boolean isadmin) {
		this.nombre = nombre;
		this.apellidos = apellido1;
		this.correo = correo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellido1) {
		this.apellidos = apellido1;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

}
