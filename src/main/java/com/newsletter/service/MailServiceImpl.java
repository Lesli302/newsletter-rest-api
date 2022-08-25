package com.newsletter.service;

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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.newsletter.persistence.entity.User;
import com.newsletter.persistence.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailServiceImpl implements MailService{
	
	@Autowired private JavaMailSender javaMailSender;
	
	@Autowired 
	private UserRepository userRepository;
	 
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
			List<String> emails = new ArrayList<>();
			usersFounded.forEach(k -> emails.add(k.getEmail()));
			if (!emails.isEmpty())
				return sendAttachmentEmail(file.getName(), initialStream.readAllBytes(), emails);
			else
				return "";
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "";
    }
	
    
    private String sendAttachmentEmail (String fileName, byte[] file, List<String> destinatarios) {
    	log.info("Adjuntando archivo.");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
 
        try {
 
            mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(destinatarios.stream().toArray(String[]::new));
            mimeMessageHelper.setText("Te enviamos la noticia de hoy. <br/><br/>Para no recibir mas correos da click en el siguiente link: <a href=''>unsuscribe</a>", true);
            mimeMessageHelper.setSubject("Newsletter");

            Tika tika = new Tika();
            String mimeType = tika.detect(file);
            log.info("mimeType. {}", mimeType);
            DataSource datasource;
            if (mimeType.equals("application/pdf"))
            	datasource = new ByteArrayDataSource(file, mimeType);
            else {
            	//BodyPart imagen = new MimeBodyPart();
            	datasource = new ByteArrayDataSource(file, "text/html");
            }
            mimeMessageHelper.addAttachment(fileName, datasource);
 
            javaMailSender.send(mimeMessage);
        	
            log.info("Success.");
            return "Correo enviado.";
        }
        catch (MessagingException e) {
        	log.error("Fail. {}", e.getMessage());
            return "Error al enviar el correo.";
        }
	}

}
