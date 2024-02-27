package com.tatademy.controller;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tatademy.model.Course;
import com.tatademy.model.Material;
import com.tatademy.model.Review;
import com.tatademy.repository.CursoRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CourseController {

	@Autowired
	private CursoRepository coursesList;

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
		coursesList.save(course);

		return "redirect-/create/course"; // MODIFY THIS AT SOME POINT TO REDIRECT TO COURSES
	}
	
	@GetMapping("/courses")
	public String getMethodName(Model model) throws SQLException {
		List<String> filters = coursesList.findAllCategories();
		List<Course> courses = new ArrayList<>();
		List<String[]> filterPair = new ArrayList<>();
		List<String[]> courseInfo = new ArrayList<>();
		List<Map<String, Object>> coursesModel = new ArrayList<>();
		Double valoration = 0.0;
		for (int i = 0; i < filters.size(); ++i) {
			String[] aux = new String[2];
			aux[0] = filters.get(i);
			aux[1] = "";
			filterPair.add(aux);
		}
		courses = coursesList.findAll();
		for (int i = 0; i < courses.size(); ++i) {
			String[] aux = new String[5];
			List<Review> reviews = new ArrayList<>();
			if (reviews.size() > 0) {
				for (int j = 0; j < reviews.size(); ++j) {
					valoration += reviews.get(i).getStarsValue();
				}
				valoration = valoration / reviews.size();
			}
			aux[0] = courses.get(i).getName();
			aux[1] = String.valueOf(reviews.size());
			aux[2] = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(courses.get(i).getImageFile().getBytes(1, (int) courses.get(i).getImageFile().length()));
			aux[3] = String.valueOf(valoration);
			aux[4] = "";
			courseInfo.add(aux);
		}
		for (String[] aux : courseInfo) {
		    Map<String, Object> courseData = new HashMap<>();
		    valoration = Double.parseDouble(aux[3]);
		    List<Map<String, Object>> stars = new ArrayList<>();
		    for (int i = 0; i < 5; i++) {
		        Map<String, Object> star = new HashMap<>();
		        star.put("filled", i < valoration);
		        stars.add(star);
		    }
		    courseData.put("0", aux[0]);
		    courseData.put("1", aux[1]);
		    courseData.put("2", aux[2]);
		    courseData.put("3", String.valueOf(valoration));
		    courseData.put("stars", stars);
		    coursesModel.add(courseData);
		}
		model.addAttribute("newest", "selected");
		model.addAttribute("valoration", "");
		model.addAttribute("mostReviewed", "");
		model.addAttribute("search", "");
		model.addAttribute("courses", coursesModel);
		model.addAttribute("filters", filterPair);
		return "course-grid";
	}

	@GetMapping("/course-search")
	public String courseSearch(Model model, @RequestParam String name, @RequestParam String sellist1) throws SQLException {
		List<Course> courses = new ArrayList<>();
		List<String> filters = coursesList.findAllCategories();
		List<String[]> filterPair = new ArrayList<>();
		List<String[]> courseInfo = new ArrayList<>();
		List<Map<String, Object>> coursesModel = new ArrayList<>();
		Double valoration = 0.0;
		String newest, valorationtxt, mostReviewed;
		for (int i = 0; i < filters.size(); ++i) {
			String[] aux = new String[2];
			aux[0] = filters.get(i);
			aux[1] = "";
			filterPair.add(aux);
		}
		courses = coursesList.findByNameContains(name);
		switch(sellist1) {
		case "Valoración":
			newest = "";
			valorationtxt = "selected";
			mostReviewed = "";
			Collections.sort(courses, new Comparator<Course>() {
			    public int compare(Course c1, Course c2) {
			        Double val1 = 0.0;
			        Double val2 = 0.0;
			        for (int i = 0; i < c1.getReviews().size(); ++i) {
			        	val1 += c1.getReviews().get(i).getStarsValue();
			        }
			        val1 = val1 / c1.getReviews().size();
			        for (int j = 0; j < c2.getReviews().size(); ++j) {
			        	val2 += c2.getReviews().get(j).getStarsValue();
			        }
			        val2 = val2 / c2.getReviews().size();
			        if (val1 < val2) {
			            return -1;
			        } else if (val1 > val2) {
			            return 1;
			        } else {
			            return 0;
			        }
			    }
			});
			break;
		case "Más veces valorado":
			newest = "";
			valorationtxt = "";
			mostReviewed = "selected";
			Collections.sort(courses, new Comparator<Course>() {
			    public int compare(Course c1, Course c2) {
			        int size1 = c1.getReviews().size();
			        int size2 = c2.getReviews().size();

			        if (size1 < size2) {
			            return -1;
			        } else if (size1 > size2) {
			            return 1;
			        } else {
			            return 0;
			        }
			    }
			});
			break;
		default:
			newest = "selected";
			valorationtxt = "";
			mostReviewed = "";
			Collections.sort(courses, new Comparator<Course>() {
				public int compare(Course c1, Course c2) {
					return c1.getcreationDate().compareTo(c2.getcreationDate());
				}
			});
			break;
		}
		
		for (int i = 0; i < courses.size(); ++i) {
			String[] aux = new String[5];
			List<Review> reviews = new ArrayList<>();
			if (reviews.size() > 0) {
				for (int j = 0; j < reviews.size(); ++j) {
					valoration += reviews.get(i).getStarsValue();
				}
				valoration = valoration / reviews.size();
			}
			aux[0] = courses.get(i).getName();
			aux[1] = String.valueOf(reviews.size());
			aux[2] = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(courses.get(i).getImageFile().getBytes(1, (int) courses.get(i).getImageFile().length()));
			aux[3] = String.valueOf(valoration);
			aux[4] = "";
			courseInfo.add(aux);
		}
		for (String[] aux : courseInfo) {
		    Map<String, Object> courseData = new HashMap<>();
		    valoration = Double.parseDouble(aux[3]);
		    List<Map<String, Object>> stars = new ArrayList<>();
		    for (int i = 0; i < 5; i++) {
		        Map<String, Object> star = new HashMap<>();
		        star.put("filled", i < valoration);
		        stars.add(star);
		    }
		    courseData.put("0", aux[0]);
		    courseData.put("1", aux[1]);
		    courseData.put("2", aux[2]);
		    courseData.put("3", String.valueOf(valoration));
		    courseData.put("stars", stars);
		    coursesModel.add(courseData);
		}
		switch(sellist1) {
		case "Publicado recientemente":
			newest = "selected";
			valorationtxt = "";
			mostReviewed = "";
			break;
		case "Valoración":
			newest = "";
			valorationtxt = "selected";
			mostReviewed = "";
			break;
		case "Más veces valorado":
			newest = "";
			valorationtxt = "";
			mostReviewed = "selected";
			break;
		default:
			newest = "selected";
			valorationtxt = "";
			mostReviewed = "";
			break;
		}
		model.addAttribute("newest", newest);
		model.addAttribute("valoration", valorationtxt);
		model.addAttribute("mostReviewed", mostReviewed);
		model.addAttribute("search", name);
		model.addAttribute("filters", filterPair);
		model.addAttribute("courses", coursesModel);
		return "course-grid";
	}

	@GetMapping("/course-filter")
	public String processForm(Model model, HttpServletRequest request) throws SQLException {
		Map<String, String[]> paramMap = request.getParameterMap();
		List<String> allFilters = coursesList.findAllCategories();
		List<String> filters = new ArrayList<>();
		List<String> filtersMode = new ArrayList<>();
		List<String> filtersName = new ArrayList<>();
		List<String[]> filterPair = new ArrayList<>();
		List<Course> courses = new ArrayList<>();
		List<String[]> courseInfo = new ArrayList<>();
		List<Map<String, Object>> coursesModel = new ArrayList<>();
		Double valoration = 0.0;
		if (!paramMap.isEmpty()) {
			for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
				String paramName = entry.getKey();
				String[] paramValues = entry.getValue();
				filtersName.add(paramName);
				for (String paramValue : paramValues) {
					filtersMode.add(paramValue);
					if (paramValue.equals("on")) {
						filters.add(paramName);
					}
				}
			}
			courses = coursesList.findByCategoriyIn(filters);
			for (int i = 0; i < courses.size(); ++i) {
				String[] aux = new String[5];
				List<Review> reviews = new ArrayList<>();
				if (reviews.size() > 0) {
					for (int j = 0; j < reviews.size(); ++j) {
						valoration += reviews.get(i).getStarsValue();
					}
					valoration = valoration / reviews.size();
				}
				aux[0] = courses.get(i).getName();
				aux[1] = String.valueOf(reviews.size());
				aux[2] = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(courses.get(i).getImageFile().getBytes(1, (int) courses.get(i).getImageFile().length()));
				aux[3] = String.valueOf(valoration);
				aux[4] = "";
				courseInfo.add(aux);
			}
			for (String[] aux : courseInfo) {
			    Map<String, Object> courseData = new HashMap<>();
			    valoration = Double.parseDouble(aux[3]);
			    List<Map<String, Object>> stars = new ArrayList<>();
			    for (int i = 0; i < 5; i++) {
			        Map<String, Object> star = new HashMap<>();
			        star.put("filled", i < valoration);
			        stars.add(star);
			    }
			    courseData.put("0", aux[0]);
			    courseData.put("1", aux[1]);
			    courseData.put("2", aux[2]);
			    courseData.put("3", String.valueOf(valoration));
			    courseData.put("stars", stars);
			    courseData.put("5", "");
			    coursesModel.add(courseData);
			}
			model.addAttribute("courses", coursesModel);
		} else {
			courses = coursesList.findAll();
			for (int i = 0; i < courses.size(); ++i) {
				String[] aux = new String[5];
				List<Review> reviews = new ArrayList<>();
				if (reviews.size() > 0) {
					for (int j = 0; j < reviews.size(); ++j) {
						valoration += reviews.get(i).getStarsValue();
					}
					valoration = valoration / reviews.size();
				}
				aux[0] = courses.get(i).getName();
				aux[1] = String.valueOf(reviews.size());
				aux[2] = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(courses.get(i).getImageFile().getBytes(1, (int) courses.get(i).getImageFile().length()));
				aux[3] = String.valueOf(valoration);
				aux[4] = "";
				courseInfo.add(aux);
			}
			for (String[] aux : courseInfo) {
			    Map<String, Object> courseData = new HashMap<>();
			    valoration = Double.parseDouble(aux[3]);
			    List<Map<String, Object>> stars = new ArrayList<>();
			    for (int i = 0; i < 5; i++) {
			        Map<String, Object> star = new HashMap<>();
			        star.put("filled", i < valoration);
			        stars.add(star);
			    }
			    courseData.put("0", aux[0]);
			    courseData.put("1", aux[1]);
			    courseData.put("2", aux[2]);
			    courseData.put("3", String.valueOf(valoration));
			    courseData.put("stars", stars);
			    coursesModel.add(courseData);
			}
			model.addAttribute("courses", coursesModel);
		}
		for (int i = 0; i < allFilters.size(); ++i) {
			String[] aux = new String[2];
			aux[0] = allFilters.get(i);
			if (filters.contains(allFilters.get(i))) {
				aux[1] = "checked";
			} else {
				aux[1] = "";
			}
			filterPair.add(aux);
		}
		model.addAttribute("newest", "selected");
		model.addAttribute("valoration", "");
		model.addAttribute("mostReviewed", "");
		model.addAttribute("search", "");
		model.addAttribute("filters", filterPair);
		return "course-grid";
	}

}
