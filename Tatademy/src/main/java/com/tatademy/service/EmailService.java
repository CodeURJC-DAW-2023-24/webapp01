package com.tatademy.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tatademy.model.User;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String mailFrom;

	public void sendEmail(User user, String password) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			message.setFrom(new InternetAddress(mailFrom));
			message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(user.getEmail()));
			message.setSubject("Tatademy - Recuperar contraseña");

			String htmlTemplate = readTemplate("src/main/resources/templates/email-template.html");
			String htmlContent = htmlTemplate.replace("${user}", user.getName());
			htmlContent = htmlContent.replace("${password}", password);

			message.setContent(htmlContent, "text/html; charset=utf-8");

			javaMailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String readTemplate(String path) throws IOException {
		Path filePath = Path.of(path);
		return Files.readString(filePath, StandardCharsets.UTF_8);
	}

}