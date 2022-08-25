package com.newsletter.controller;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.newsletter.service.UserService;


@RestController
@RequestMapping("/userApi") 
public class UserController {
	
	final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
    private UserService userService;
	
	
	@PostMapping(path="/addUser")
    public ResponseEntity<Object> addUser(@RequestBody String email) {
		logger.info("Servicio para agregar usuario.");
        
        userService.save(email);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
	
	@PostMapping(path="/addList")
    public ResponseEntity<Object> addList(@RequestParam("file") MultipartFile file) {
		logger.info("Servicio para agregar lista de usuarios.");
        
        userService.addList(file);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
	
	@DeleteMapping(path = "/{id}/deleteUser")
    public ResponseEntity<Object> deleteUser(@PathVariable String id) {
		logger.info("Servicio para agregar lista de usuarios.");
        
        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
	
}
