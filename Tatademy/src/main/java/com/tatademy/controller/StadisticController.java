package com.tatademy.controller;


import java.util.Calendar;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tatademy.model.Course;
import com.tatademy.model.Review;
import com.tatademy.repository.CourseRepository;
import com.tatademy.repository.ReviewRepository;
import com.tatademy.repository.UserRepository;

@Controller
public class StadisticController {
    @Autowired
	private ReviewRepository reviewRepository;
    @Autowired
	private UserRepository userRepository;
	@Autowired
	private CourseRepository courseRepository;

//-------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------
/*		toco courseController que no deberia estar aqui
*		userRepository
en html de dashboar
atributo en courses: private Calendar creationDate;
courseRepository

*
*/
//-------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------
	 //PARTE DE LAS ESTADÍSTICAS
	@GetMapping("/TatademyStadict") 
	public String tatadyStadistic(Model model) {

		//total reviews and stars average
		model.addAttribute("usersEnroll", userRepository.findUsersEnrolledInAtLeastOneCourse().size());
		List<Review> allReviews = reviewRepository.findAll();
		Double starAverage = 0.0;
		for (Review review : allReviews) {
			starAverage += review.getStarsValue();
		}
		model.addAttribute("TotalReviews", allReviews.size());
		model.addAttribute("starsAverage", starAverage/(allReviews.size()));
		
		return "instructor-dashboard";
    }


	@GetMapping("/cursesMonth")
    @ResponseBody
    public Integer[] CursesMonthUntilOct() {
        List<Course> allCourses = courseRepository.findAll();
		Integer[] coursesByMonth = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

		for (Course course : allCourses) {
			Calendar creationDate = course.getCreationDate();
			int month = creationDate.get(Calendar.MONTH);
			
			if (month >= 0 && month <= 9){
				coursesByMonth[month]++;
			}			
		}
	
		for (int i = 0; i < coursesByMonth.length; i++) {
			int cantidadCursos = coursesByMonth[i];
			System.out.println("/cursesMonth  -> Month " + (i + 1) + ": " + cantidadCursos + " courses created");
		}
		
		return coursesByMonth;
    }

	@GetMapping("/reviewsMonth")
    @ResponseBody
    public Integer[] ReviewsMonthUntilSept() {
        List<Review> allreviews = reviewRepository.findAll();
		Integer[] reviewsByMonth = {0, 0, 0, 0, 0, 0, 0, 0, 0}; 

		for (Review review : allreviews) {
			Calendar creationDate = review.getCreationDate();
			int month = creationDate.get(Calendar.MONTH);
			if (month >= 0 && month <= 8){
				reviewsByMonth[month]++;
			}
		}
		for (int i = 0; i < reviewsByMonth.length; i++) {
			int cantidadReview = reviewsByMonth[i];
			System.out.println("/reviewsMonth  -> Month " + (i + 1) + ": " + cantidadReview + " reviews created");
		}
		return reviewsByMonth;
    }	
}
