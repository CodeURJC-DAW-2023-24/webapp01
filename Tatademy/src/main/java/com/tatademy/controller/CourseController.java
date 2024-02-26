package com.tatademy.controller;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


import java.io.IOException;
import java.net.URI;
import org.springframework.http.HttpHeaders;
import java.sql.SQLException;
import java.util.List;


import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tatademy.model.Course;
import com.tatademy.model.Material;
import com.tatademy.model.Review;
import com.tatademy.model.User;

import com.tatademy.repository.CursoRepository;
import com.tatademy.repository.MaterialRepository;
import com.tatademy.repository.ReviewRepository;
import com.tatademy.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

@Controller
public class CourseController {

	@Autowired
	private CursoRepository courseRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private MaterialRepository materialRepository;
	

	@GetMapping("/create/course")
	public String getMethodName() {
		return "add-course";
	}

	@PostMapping("/create/course")
	public String postMethodName(@RequestParam String title, @RequestParam String subject,
			@RequestParam String description, @RequestParam("fileImage") MultipartFile fileImage,
			@RequestParam("courseContentInputFiles") List<MultipartFile> courseContentInputFiles) throws IOException {
		Course course = new Course(title, subject, description);
		URI location = fromCurrentRequest().build().toUri();
		course.setimageString(location.toString());
		course.setImageFile(BlobProxy.generateProxy(fileImage.getInputStream(), fileImage.getSize()));
		for (int i = 0; i < courseContentInputFiles.size(); i++) {
			Material material = new Material();
			material.setFilelocation(location.toString());
			material.setFile(BlobProxy.generateProxy(courseContentInputFiles.get(i).getInputStream(),
					courseContentInputFiles.get(i).getSize()));
			course.getMaterial().add(material);
		}
		courseRepository.save(course);

		return "redirect:/create/course"; // MODIFY THIS AT SOME POINT TO REDIRECT TO COURSES
	}

	
	@GetMapping("/{idCourse}/Courseinformation") // Show information about the selected course   
	public String getCourseInformationId(Model model, @PathVariable Integer idCourse)  {
		//GENERIC INFORMATION ABOUT DE COURSE:		
		String nameCourse = courseRepository.findNameById(idCourse);		
		model.addAttribute("nameCourse", nameCourse);

		String categoryCourse = courseRepository.findCategoryById(idCourse);		
		model.addAttribute("categoryCourse", categoryCourse);

		String descripcionCourse = courseRepository.findDescriptionById(idCourse);		
		model.addAttribute("descripcionCourse", descripcionCourse);

		List<Material> materialsFromCourse = courseRepository.findMaterialsByIdCourse(idCourse);
		model.addAttribute("materialsFromCourse", materialsFromCourse);
		

		//INFORMATION RELATED TO REVIEWS
		List<Review> reviewsFromCourse = courseRepository.findReviewsByIdCourse(idCourse);
		model.addAttribute("reviewsFromCourse", reviewsFromCourse);

		Integer numReviews =  reviewsFromCourse.size();
		model.addAttribute("numReviews", numReviews);
		Integer starAverage = 0;

		for (Review review : reviewsFromCourse) {
			starAverage = review.getStarsValue();
		}	
		model.addAttribute("starAverage", starAverage/numReviews);

		//ABOUT THE COURSE
		List<User> studentsEnroll = userRepository.findAllUserEnrollByIdCourse(idCourse);
		Integer numStudentsEnroll = studentsEnroll.size();
		model.addAttribute("numStudentsEnroll", numStudentsEnroll);
		model.addAttribute("numMaterialsCourse", materialsFromCourse.size());


		return "course-details";
	}

	@PostMapping("/saveReview") //The user adds a review to a course.
    public String saveReview(@RequestParam String userNameReview, @RequestParam String userSurnameReview, @RequestParam int rateReview, @RequestParam String descriptionReview) {
        //Create the new review:
        Review newReview = new Review();
        newReview.setStarsValue(rateReview);
        newReview.setDescription(descriptionReview);
        User userReview = userRepository.findUserByNameAndSurname(userNameReview, userSurnameReview);
        newReview.setUser(userReview);
        // Save the review
        reviewRepository.save(newReview);        
        return "¿#?"; //CHANGE
    }


	@PostMapping("/{idCourse}/enroll") //A user wants to enroll in a course
	public String enrollUserToCourse(@PathVariable Integer idCourse, HttpServletRequest request){
		// Get the current user from the security
		String name = request.getUserPrincipal().getName();
		User currentUser = userRepository.findUserByName(name);
		// Get the course with the given ID
		Course course = courseRepository.findById(idCourse).orElseThrow();
		// Add the course to the user's courses list
		currentUser.getCourses().add(course);
		userRepository.save(currentUser);
		return "¿#?"; //CHANGE
	}

	@GetMapping("/{id}/DownloadFile")   //WORKING ON IT!!! : Download the selected file. 	id->id of the selected material
	public ResponseEntity<Object> downloadImage(@PathVariable Integer id) throws SQLException {
		Material material = materialRepository.findById(id).orElseThrow();
		if (material.getFile() != null) {
			InputStreamResource file = new InputStreamResource(material.getFile().getBinaryStream());
			return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, "file")
				.contentLength(material.getFile().length())
				.body(file);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
