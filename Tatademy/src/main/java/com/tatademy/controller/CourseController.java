package com.tatademy.controller;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.tatademy.model.Course;
import com.tatademy.model.Material;
import com.tatademy.repository.CourseRepository;
import com.tatademy.repository.MaterialRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CourseController {

	@Autowired
	private CourseRepository courses;

	@Autowired
	private MaterialRepository materialRepository;

	@GetMapping("/new/course")
	public String getMethodName() {
		return "add-course";
	}

	@PostMapping("/new/course")
	public String postMethodName(@RequestParam String title, @RequestParam String subject,
			@RequestParam String description, @RequestParam("fileImage") MultipartFile fileImage,
			@RequestParam("courseContentInputFiles") List<MultipartFile> courseContentInputFiles) throws IOException {
		Course course = new Course(title, subject, description);
		course.setimageString(fileImage.getOriginalFilename());
		course.setImageFile(BlobProxy.generateProxy(fileImage.getInputStream(), fileImage.getSize()));
		courses.save(course);
		for (int i = 0; i < courseContentInputFiles.size(); i++) {
			Material material = new Material();
			material.setFilename(courseContentInputFiles.get(i).getOriginalFilename());
			material.setFile(BlobProxy.generateProxy(courseContentInputFiles.get(i).getInputStream(),
					courseContentInputFiles.get(i).getSize()));
			material.setCourse(course);
			materialRepository.save(material);
			course.getMaterial().add(material);
		}
		return "redirect:/new/course"; // MODIFY THIS AT SOME POINT TO REDIRECT TO COURSES
	}

	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			model.addAttribute("logged", true);
			model.addAttribute("userName", principal.getName());
			model.addAttribute("adminNewCourse", request.isUserInRole("ADMIN"));
			model.addAttribute("admin", request.isUserInRole("ADMIN"));
			model.addAttribute("user", request.isUserInRole("USER"));
		} else {
			model.addAttribute("logged", false);
		}
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
	}

	//ESTO HAY QUE MODIFICARLO
	@GetMapping("/generate-pdf")
    public ModelAndView generatePdf(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Set content type and headers for PDF response
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Certificado.pdf");

            // Create a new PDF document
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());

            // Open the document
            document.open();
			Paragraph blank = new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA,Font.DEFAULTSIZE,Font.NORMAL));
			blank.setAlignment(Paragraph.ALIGN_CENTER);
            // Add content to the PDF
			Paragraph title = new Paragraph("¡FELICIDADES!", FontFactory.getFont(FontFactory.HELVETICA,20,Font.BOLD));
			title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
			Paragraph desc = new Paragraph("Has conseguido el diploma de", FontFactory.getFont(FontFactory.HELVETICA,Font.DEFAULTSIZE,Font.NORMAL));
			desc.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(desc);
       	//ESTÁ A FUEGO CAMBIARLO
		Paragraph subject = new Paragraph("INTRODUCCIÓN BASES DE DATOS", FontFactory.getFont(FontFactory.HELVETICA,20,Font.BOLD));
		subject.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(subject);
		Paragraph desc2 = new Paragraph("Por haber completados todo el contenido del curso", FontFactory.getFont(FontFactory.HELVETICA,Font.DEFAULTSIZE,Font.NORMAL));
		desc2.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(desc2);

		document.add(blank);
		document.add(blank);
		document.add(blank);
		document.add(blank);
		document.add(blank);
		Paragraph desc3 = new Paragraph("Este diploma se entrega a:", FontFactory.getFont(FontFactory.HELVETICA,Font.DEFAULTSIZE,Font.NORMAL));
		desc2.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(desc3);	
		
			//A FUEGO CAMBIAR ESTO
		Paragraph desc4 = new Paragraph("Jesús Mariscal Alonso", FontFactory.getFont(FontFactory.HELVETICA,Font.DEFAULTSIZE,Font.NORMAL));
		desc2.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(desc4);

		Paragraph brandName = new Paragraph("Tatademy", FontFactory.getFont(FontFactory.TIMES_ROMAN,40,Font.BOLD));
		brandName.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(brandName);


            // Close the document
            document.close();

            // Flush the response
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Return null to prevent view resolution
    }
	

}
