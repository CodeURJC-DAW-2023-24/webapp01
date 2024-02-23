package com.tatademy.controller;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tatademy.model.Curso;
import com.tatademy.model.Material;
import com.tatademy.repository.CursoRepository;

@Controller
public class CourseController {

	@Autowired
	private CursoRepository courses;

	@GetMapping("/create/course")
	public String getMethodName() {
		return "add-course";
	}

	@PostMapping("/create/course")
	public String postMethodName(@RequestParam String title, @RequestParam String subject,
			@RequestParam String description, @RequestParam("fileImage") MultipartFile fileImage,
			@RequestParam("courseContentInputFiles") List<MultipartFile> courseContentInputFiles) throws IOException {
		Curso course = new Curso(title, subject, description);
		URI location = fromCurrentRequest().build().toUri();
		course.setImagen(location.toString());
		course.setImagenFile(BlobProxy.generateProxy(fileImage.getInputStream(), fileImage.getSize()));
		for (int i = 0; i < courseContentInputFiles.size(); i++) {
			Material material = new Material();
			material.setFilelocation(location.toString());
			material.setFile(BlobProxy.generateProxy(courseContentInputFiles.get(i).getInputStream(),
					courseContentInputFiles.get(i).getSize()));
			course.getMateriales().add(material);
		}
		courses.save(course);

		return "redirect:/create/course"; // MODIFY THIS AT SOME POINT TO REDIRECT TO COURSES
	}

}
