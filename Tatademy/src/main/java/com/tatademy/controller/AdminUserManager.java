package com.tatademy.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.core.io.Resource;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tatademy.model.User;
import com.tatademy.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


    
@Controller
public class AdminUserManager {
    
    @Autowired
	private UserRepository users;

    @GetMapping("/admin/users")
    public String getUsers(@RequestParam(defaultValue = "0") int page, Model model) throws SQLException {
        Pageable pageable = PageRequest.of(page, 10);
        Page<User> usersPage = users.findAll(pageable);
        for (int i =0 ; i < usersPage.getNumberOfElements(); i++) {
            if (usersPage.getContent().get(i).getImageFile() != null) {
            usersPage.getContent().get(i).setImage( "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(usersPage.getContent().get(i).getImageFile().getBytes(1, (int) usersPage.getContent().get(i).getImageFile().length())));
            
        }
        }
        model.addAttribute("numUsers", usersPage.getNumberOfElements());
        model.addAttribute("numUsersMax", users.findAll().size());
         model.addAttribute("users", usersPage);
         model.addAttribute("hasNext", usersPage.hasNext());
         model.addAttribute("currentPage", pageable.getPageNumber()+1);
         model.addAttribute("searched", "");
         
            
        return "instructor-edit-profile";
    }
    @PutMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
       users.deleteById(id);
        
        return "redirect:/users";
    }

    @PostMapping("/update")
    public String userinfo(Model model, @RequestParam int userId) throws SQLException {
       User user = users.findById(userId).orElseThrow();
        if (user.getImageFile() != null) {
            user.setImage( "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(user.getImageFile().getBytes(1, (int) user.getImageFile().length())));
        
    }
    
       model.addAttribute("user", user);
        return "edit-other-profiles";
    }
    @PostMapping("/admin/user/deleteImage")
    public String deleteimageProfile(Model model,@RequestParam int id) {
        
        User user = users.findById(id).orElseThrow();
        user.setImage(null);
        user.setImageFile(null);
        users.save(user);
        model.addAttribute("user", user);
        return "edit-other-profiles";
        
    }

    @PostMapping("/admin/user/profile")
    public String postMethodName(@RequestParam int id, @RequestParam("fileImage") MultipartFile fileImage, @RequestParam String name, @RequestParam String surname, @RequestParam String email) throws IOException {
       User user = users.findById(id).orElseThrow();
       user.setName(name);
       user.setSurname(surname);
       user.setImage(null);
       user.setImageFile(BlobProxy.generateProxy(fileImage.getInputStream(), fileImage.getSize()));
       user.setEmail(email);
       users.save(user);
        
        return "redirect:/users";
    }
    @GetMapping("/admin/search-user")
    public String searchUser(@RequestParam String email, Model model) {
    	int page = 0;
    	Pageable pageable = PageRequest.of(page, 2);
    	Page<User> usersPage = users.findByEmail(pageable, email);
    	if (email == "") {
    		usersPage = users.findAll(pageable);
    	}
    	else {
            usersPage = users.findByEmail(pageable, email);
    	}
        model.addAttribute("numUsers", usersPage.getNumberOfElements());
        model.addAttribute("numUsersMax", users.findAll().size());
        model.addAttribute("users", usersPage);
        model.addAttribute("hasNext", usersPage.hasNext());
        model.addAttribute("currentPage", pageable.getPageNumber()+1);
        model.addAttribute("searched", email);
    	return "instructor-edit-profile";
    }

    
    


    
    
    

    
    
    
}
