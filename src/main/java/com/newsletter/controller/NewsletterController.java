package com.newsletter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.newsletter.service.MailService;


@RestController
@RequestMapping("/newsletterApi") 
public class NewsletterController {
	
	final Logger logger = LoggerFactory.getLogger(NewsletterController.class);
	
	@Autowired
    private MailService mailService;
	
	
	@PostMapping(path="/sendNewsletter")
    public ResponseEntity<Object> sendNewsletter(@RequestParam("file") MultipartFile file) {
		// TODO Agregar swagger
		logger.info("Servicio para enviar de newsletter.");
        
        mailService.sendNewsletter(file);

        return new ResponseEntity<>(HttpStatus.OK);

    }
	
}
