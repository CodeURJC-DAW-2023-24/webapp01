package com.tatademy.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tatademy.model.Course;
import com.tatademy.model.Review;
import com.tatademy.model.User;
import com.tatademy.repository.CourseRepository;
import com.tatademy.repository.ReviewRepository;
import com.tatademy.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class DatabaseInitializerService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${security.user}")
	private String adminMail;

	@Value("${security.encodedPassword}")
	private String encodedPassword;

	@PostConstruct
	public void init() throws IOException, URISyntaxException {
		// Sample users
		User user1 = new User("Jesús", "Mariscal Alonso", "jesus@tatademy.com", passwordEncoder.encode("jesus"));
		user1.setRoles(List.of("USER"));
		userRepository.save(user1);

		User user2 = new User("Pedro", "Torrecilla", "pedro@tatademy.com", passwordEncoder.encode("pedro"));
		user2.setRoles(List.of("USER"));
		userRepository.save(user2);

		User user3 = new User("Enrique", "Tentor Martín", "quique@tatademy.com", passwordEncoder.encode("quique"));
		user3.setRoles(List.of("USER"));
		userRepository.save(user3);

		User user4 = new User("Javier", "Bringas García", "javier@tatademy.com", passwordEncoder.encode("javier"));
		user4.setRoles(List.of("USER"));
		userRepository.save(user4);

		User user5 = new User("Paula", "Ruiz Rubio", "paula@tatademy.com", passwordEncoder.encode("paula"));
		user5.setRoles(List.of("USER"));
		userRepository.save(user5);

		User admin = new User("admin", "tatademy", adminMail, encodedPassword);
		admin.setRoles(List.of("USER", "ADMIN"));
		try (InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream("static/assets/img/user/admin_image.jpg")) {
			byte[] imageData = inputStream.readAllBytes();
			SerialBlob imageBlob = new SerialBlob(imageData);
			admin.setImageFile(imageBlob);
			admin.setImage("admin_image.jpg");
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		userRepository.save(admin);

		// Sample reviews
		Review review1 = new Review(1, "No me ha gustado nada, muy mejorable :(", user1);
		Review review2 = new Review(2, "He aprendido poco, es muy para principiantes", user2);
		Review review3 = new Review(3, "Me ha servido, aunque hay cosas que he tenido que buscar en internet", user3);
		Review review4 = new Review(4,
				"Es un curso fantastico, aunque podrían poner los comandos en un archivo adjunto", user4);
		Review review5 = new Review(5, "Perfecto, gracias a este curso soy mucho mejor profesional", user5);
		Review review6 = new Review(5, "Perfecto, gracias a este curso soy mucho mejor profesional", user3);
		Review review7 = new Review(1, "Demasiado básico para mi nivel, esperaba más contenido avanzado", user5);
		Review review8 = new Review(2, "Muy completo y bien explicado, lo recomendaría a cualquier persona interesada",
				user5);
		Review review9 = new Review(3, "No he encontrado útiles las explicaciones, demasiado confusas", user1);
		Review review10 = new Review(4, "Buena introducción al tema, pero necesitaría más ejercicios prácticos", user4);
		Review review11 = new Review(5, "Increíble, he aprendido tanto en tan poco tiempo, gracias al equipo del curso",
				user2);
		Review review12 = new Review(1, "El curso me ha decepcionado, no cubre las expectativas que tenía", user5);

		// Sample courses
		Course course1 = new Course("Desarrollo Web Completo con HTML5, CSS3, JS AJAX PHP y MySQL", "Software",
				"Aprende Desarrollo Web con este curso 100% práctico, paso a paso y sin conocimientos previo");
		course1.setReviews(List.of(review1, review6, review7, review10, review11));
		try (InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream("static/assets/img/course/course-05.jpg")) {
			byte[] imageData = inputStream.readAllBytes();
			SerialBlob imageBlob = new SerialBlob(imageData);
			course1.setImageFile(imageBlob);
			course1.setimageString("course-05.jpg");
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		courseRepository.save(course1);
		review1.setCourse(course1);
		review1.setCreationDate(Calendar.getInstance());
		reviewRepository.save(review1);
		review6.setCourse(course1);
		review6.setCreationDate(Calendar.getInstance());
		reviewRepository.save(review6);
		review7.setCourse(course1);
		review7.setCreationDate(Calendar.getInstance());
		reviewRepository.save(review7);
		review11.setCourse(course1);
		review11.setCreationDate(Calendar.getInstance());
		reviewRepository.save(review11);
		review10.setCourse(course1);
		review10.setCreationDate(Calendar.getInstance());
		reviewRepository.save(review10);

		Course course2 = new Course("Angular: De cero a experto (Legacy)", "Software",
				"Todo lo que necesitas saber de angular utilizando TypeScript y buenas prácticas ofrecidas por el equipo de angular.");
		course2.setReviews(List.of(review2, review9));
		try (InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream("static/assets/img/course/course-13.jpg")) {
			byte[] imageData = inputStream.readAllBytes();
			SerialBlob imageBlob = new SerialBlob(imageData);
			course2.setImageFile(imageBlob);
			course2.setimageString("course-13.jpg");
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		courseRepository.save(course2);
		review2.setCourse(course2);
		review2.setCreationDate(Calendar.getInstance());
		reviewRepository.save(review2);
		review9.setCourse(course2);
		review9.setCreationDate(Calendar.getInstance());
		reviewRepository.save(review9);

		Course course3 = new Course("Master en JavaScript: Aprender JS, jQuery, Angular, NodeJS", "Software",
				"Aprende a programar desde cero y desarrollo web con JavaScript, jQuery, JSON, TypeScript, Angular, Node, MEAN.");
		course3.setReviews(List.of(review3));
		try (InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream("static/assets/img/course/course-07.jpg")) {
			byte[] imageData = inputStream.readAllBytes();
			SerialBlob imageBlob = new SerialBlob(imageData);
			course3.setImageFile(imageBlob);
			course3.setimageString("course-07.jpg");
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		courseRepository.save(course3);
		review3.setCourse(course3);
		review3.setCreationDate(Calendar.getInstance());
		reviewRepository.save(review3);

		Course course4 = new Course("Curso Maestro de Python: De Cero a Programador Todoterreno", "Software",
				"Aprenderás Python y sus mejores módulos: Tkinter, SQLite, Numpy, Pandas, Matplotlib, Pipenv, Django, FastAPI, Bs4 y más!");
		course4.setReviews(List.of(review4, review8));
		try (InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream("static/assets/img/course/course-06.jpg")) {
			byte[] imageData = inputStream.readAllBytes();
			SerialBlob imageBlob = new SerialBlob(imageData);
			course4.setImageFile(imageBlob);
			course4.setimageString("course-06.jpg");
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		courseRepository.save(course4);
		review4.setCourse(course4);
		review4.setCreationDate(Calendar.getInstance());
		reviewRepository.save(review4);
		review8.setCourse(course4);
		review8.setCreationDate(Calendar.getInstance());
		reviewRepository.save(review8);

		Course course5 = new Course("React: De cero a experto ( Hooks y MERN )", "Software",
				"Context API, MERN, Hooks, Firestore, JWT, Testing, Autenticaciones, Despliegues, CRUD, Logs, MUI, Multiple Routers...");
		course5.setReviews(List.of(review5));
		try (InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream("static/assets/img/course/course-11.jpg")) {
			byte[] imageData = inputStream.readAllBytes();
			SerialBlob imageBlob = new SerialBlob(imageData);
			course5.setImageFile(imageBlob);
			course5.setimageString("course-11.jpg");
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		courseRepository.save(course5);
		review5.setCourse(course5);
		review5.setCreationDate(Calendar.getInstance());
		reviewRepository.save(review5);

		Course course6 = new Course("Programación de Android desde cero con Java", "Software",
				"Aprender a programar aplicaciones y juegos para Android de forma profesional y desde cero.");
		course6.setReviews(List.of(review12));
		try (InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream("static/assets/img/course/course-14.jpg")) {
			byte[] imageData = inputStream.readAllBytes();
			SerialBlob imageBlob = new SerialBlob(imageData);
			course6.setImageFile(imageBlob);
			course6.setimageString("course-14.jpg");
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		courseRepository.save(course6);
		review12.setCourse(course6);
		review12.setCreationDate(Calendar.getInstance());
		reviewRepository.save(review12);

		user1.setCourses(List.of(course1));
		userRepository.save(user1);

		user2.setCourses(List.of(course1, course2));
		userRepository.save(user2);

		user3.setCourses(List.of(course1, course2, course3));
		userRepository.save(user3);

		user4.setCourses(List.of(course1, course2, course3, course4));
		userRepository.save(user4);

		user5.setCourses(List.of(course1, course2, course3, course4, course5));
		userRepository.save(user5);

	}

}