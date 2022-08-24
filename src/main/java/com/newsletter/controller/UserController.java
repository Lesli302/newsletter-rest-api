package com.newsletter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.newsletter.service.UserService;


@RestController
@RequestMapping("/newsletterApi") 
public class UserController {
	
	final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
    private UserService userService;
	
	
	@PostMapping(path="/addUser")
    public ResponseEntity<String> addUser(@RequestBody String email) {
		logger.info("Servicio para agregar usuario.");
        
        String res = userService.save(email);

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }
	
	@PostMapping(path="/sendNewsletter")
    public ResponseEntity<String> sendNewsletter(@RequestParam("file") MultipartFile file) {
		logger.info("Servicio para enviar de newsletter.");
        
        String res = userService.sendNewsletter(file);

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }
	
	@PostMapping(path="/addList")
    public ResponseEntity<String> addList(@RequestParam("file") MultipartFile file) {
		logger.info("Servicio para agregar lista de usuarios.");
        
        String res = userService.addList(file);

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }
	
}
