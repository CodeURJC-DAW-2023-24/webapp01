package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.OnDelete;


@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private int valorEstrellas;
    private String descripcion;
    @ManyToOne
    @JsonIgnore
    private Usuario user;


    public Review() {}

    public Review(int estrellas, String desc) {

        this.valorEstrellas=estrellas;
        descripcion = desc;

    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public int getValorEstrellas() {
        return valorEstrellas;
    }

    public void setValorEstrellas(int valorEstrellas) {
        this.valorEstrellas = valorEstrellas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
