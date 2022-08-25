package com.newsletter.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.newsletter.controller.UserController;
import com.newsletter.persistence.entity.User;
import com.newsletter.persistence.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired 
	private JavaMailSender javaMailSender;
	
	@Autowired 
	private UserRepository userRepository;
	 
	
	@Override
    public String save(String email) {
		
		String res="OK";
		//  solo agrega el correo si no existe en la bd
		
		List<User> usersFounded = userRepository.findByEmail(email);
		log.info("resultado: {}", usersFounded);
		List<String> emails = new ArrayList<>();
		usersFounded.forEach(k -> emails.add(k.getEmail()));
		if (!emails.contains(email)) {
			log.info("Se agrega correo: {}", email);
			User user = new User();
			user.setEmail(email);
    		User userRes = userRepository.insert(user);
		}
		else {
			log.info("Correo ya existente: {}", email);
			res="FAIL";
		}
        return res;
    }
	
	
	@Override
    public String addList(MultipartFile file) {
		try {
			if (!file.isEmpty()) {
				InputStream initialStream = file.getInputStream();
				Scanner obj = new Scanner(initialStream);
			    while (obj.hasNextLine()) {
			    	log.info("Correo a insertar: {}", (obj.nextLine()));
			    	User user = new User();
			    	user.setEmail(obj.nextLine());
			    	User userRes = userRepository.insert(user);
			    }
			   
			} else {
			      return "You failed to upload " + file.getName() + " because the file was empty.";
			}
	        	
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "";
    }
	
	@Override
    public void delete(String id) {
		
		String res="OK";
		
		userRepository.deleteById(id);
		log.info("Se elimina correo", id);
		
    }
	
}
