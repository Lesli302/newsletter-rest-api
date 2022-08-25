package com.newsletter.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.newsletter.persistence.entity.User;
import com.newsletter.persistence.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailServiceImpl implements MailService{
	
	@Autowired private JavaMailSender javaMailSender;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private TemplateEngine templateEngine;
	 
	@Value("${spring.mail.username}") 
    private String sender;
    
    @Value("${spring.mail.password}") 
    private String pass;
	
    @Override
    public String sendNewsletter(MultipartFile file) {
		try {
			log.info("Enviando correo.");
			InputStream initialStream = file.getInputStream();
			List<User> usersFounded = userRepository.findAll();
			if (!usersFounded.isEmpty())
				return sendAttachmentEmail(file.getName(), initialStream.readAllBytes(), usersFounded);
			else
				return "";
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "";
    }

    
    private String sendAttachmentEmail (String fileName, byte[] bytes, List<User> usersFounded) {
    	log.info("Adjuntando archivo.");
    	
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        
        try {
        	MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setSubject("Newsletter");

            Tika tika = new Tika();
            String mimeType = tika.detect(bytes);
            DataSource datasource = new ByteArrayDataSource(bytes, mimeType);

            mimeMessageHelper.addAttachment(fileName, datasource);
           
            for(User user:usersFounded) {
            	mimeMessageHelper.setTo(user.getEmail());
	            Context context =  new Context();
	        	context.setVariable("email", user.getEmail());
	        	String path="http://localhost:8080/userApi/" + user.getDocumentId() + "/deleteUser";
	        	context.setVariable("unsuscribe", path);
	        	String htmlContent = templateEngine.process("template-newsletter",context);
	        	mimeMessageHelper.setText(htmlContent, true);
	            javaMailSender.send(mimeMessage);
	            log.info("Correo enviado a: {}", user.getEmail());
            }
            
            return "Correo enviado.";
        }
        catch (MessagingException e) {
        	log.error("Fail. {}", e.getMessage());
            return "Error al enviar el correo.";
        }
	}

}
