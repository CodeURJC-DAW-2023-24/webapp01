package com.example.demo.controlers;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.model.Curso;
import com.example.demo.model.Review;
import com.example.demo.model.Usuario;
import com.example.demo.repositories.CursoRepository;
import com.example.demo.repositories.ReviewRepository;
import com.example.demo.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class RestControler {

    @Autowired
    private CursoRepository cursos;

    //@Autowired
    //private MaterialRepositoy materiales;
    @Autowired
    private ReviewRepository reviews;

    @Autowired
    private UserRepository users;

    //CRUD USERS
    @GetMapping("/users")
    public List<Usuario> getAllUsers() {
        return users.findAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Usuario> getUser(@PathVariable int id) {

        try {
        Usuario user = users.getReferenceById(id);
        return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/new/user")
    public ResponseEntity<Usuario> postUser(@RequestBody Usuario user) {
        this.users.save(user);
        URI location = fromCurrentRequest().path("/user/{id}")
        .buildAndExpand(users.getReferenceById(user.getId())).toUri();

        return ResponseEntity.created(location).body(user);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Usuario> putUser(@PathVariable Integer id, @RequestBody Usuario user) {
        try {
            Usuario oldUser = users.getReferenceById(id);
            user.setId(id);
            users.save(user);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Usuario> DeleteUser(@PathVariable Integer id) {
        try {
            Usuario user = users.getReferenceById(id);
            users.deleteById(id);
            //Esta línea emite error y no entiendo porqué, pero funciona bien
            return ResponseEntity.ok(user);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
        //! CRUD USERS

        //CRUD Cursos
        @GetMapping("/courses")
        public List<Curso> getMethodName() {
            return cursos.findAll();
        }

        @PostMapping("/admin/{id}/new/course")
        public ResponseEntity<Curso> postMethodName(@PathVariable Integer id, @RequestBody Curso course) {
            Usuario usuario = users.findById(id).orElseThrow();
            usuario.getCursos().add(course);
            users.save(usuario);
            return ResponseEntity.ok(course);
        }

        @PutMapping("/new/course")
        public ResponseEntity<Curso> putMethodName(@RequestBody Curso course) {
            Curso curso = cursos.findById(course.getId()).orElseThrow();

            return ResponseEntity.ok(curso);
        }
        @DeleteMapping("/course/{courseId}")
        public ResponseEntity<Curso> deleteCourse(@PathVariable Integer courseId){
            Curso curso = cursos.findById(courseId).orElseThrow();
            cursos.deleteById(curso.getId());
            return ResponseEntity.ok(curso);
        }


        //!CRUD Cursos

        //CRUD REVIEWS
        @GetMapping("/reviews")
        public List<Review> getReviews() {
            return reviews.findAll();
        }



       /* @PostMapping("/course/{idCourse}/new/review/{idUser}")
        public ResponseEntity<Review> postMethodName(@PathVariable Integer idCourse, @PathVariable Integer idUser, @RequestBody Review review) {
            Usuario user = users.findById(idUser).orElseThrow();
            Curso course = cursos.findById(idCourse).orElseThrow();

            users.save(user);
            course.getReviews().add(review);
            cursos.save(course);


            return ResponseEntity.ok(review);
        }*/







        //!CRUD REVIEWS










}
