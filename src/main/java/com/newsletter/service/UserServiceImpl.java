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
	private UserRepository userRepository;
	 
	
	@Override
    public User save(String email) {
		// TODO Validar formato de correos 
		//  solo se agrega el correo si no existe en la bd
		User userRes = null;
		List<User> usersList = userRepository.findByEmail(email);
		if (usersList.isEmpty()) {
			log.info("Se agrega correo: {}", email);
			User user = new User();
			user.setEmail(email);
    		userRes = userRepository.save(user);
		}
		else {
			log.info("Correo ya existente: {}", email);
		}
		return userRes;
    }
	
	
	@Override
    public void addList(MultipartFile file) {
		try {
			if (!file.isEmpty()) {
				InputStream initialStream = file.getInputStream();
				Scanner obj = new Scanner(initialStream);
			    while (obj.hasNextLine()) {
			    	User u = save(obj.nextLine());
			    }
			}
			else
				log.info("Archivo vac\u00EDo");
		} catch (IOException e1) {
			log.error("Error al agregar lista de usuario. {}", e1.getMessage());
		}
    }
	
	@Override
    public void delete(String id) {
		userRepository.deleteById(id);
		log.info("Se elimina correo. ");
    }
	
}
