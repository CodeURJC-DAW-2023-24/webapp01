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

    @GetMapping("/users")
    public String getUsers(@RequestParam(defaultValue = "0") int page, Model model) throws SQLException {
        Pageable pageable = PageRequest.of(page, 2);
        Page<User> usersPage = users.findAll(pageable);


        

        model.addAttribute("numUsers", usersPage.getNumberOfElements());
        model.addAttribute("numUsersMax", users.findAll().size());
         model.addAttribute("users", usersPage);
         model.addAttribute("hasNext", usersPage.hasNext());
         model.addAttribute("currentPage", pageable.getPageNumber()+1);
         
            
        return "instructor-edit-profile";
    }
    @PutMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
       users.deleteById(id);
        
        return "redirect:/users";
    }
    

    
    
    
}
