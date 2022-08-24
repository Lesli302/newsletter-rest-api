package com.newsletter.service;

import java.io.File;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailServiceImpl {
	
	final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired private JavaMailSender javaMailSender;
	 
    @Value("${spring.mail.username}") private String sender;
	
	public String sendAttachmentEmail (String fileName, byte[] file, List<String> destinatarios) {
		logger.info("Adjuntando archivo.");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
 
        try {
 
            mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(destinatarios.stream().toArray(String[]::new));
            mimeMessageHelper.setText("Te enviamos la noticia de hoy");
            mimeMessageHelper.setSubject("Newsletter");
 
            FileSystemResource fileResource = new FileSystemResource(new File(fileName));
 
            mimeMessageHelper.addAttachment(fileResource.getFilename(), fileResource);
 
            javaMailSender.send(mimeMessage);
            logger.info("Success.");
            return "Mail sent Successfully";
        }
        catch (MessagingException e) {
        	logger.info("Fail.");
            return "Error while sending mail!!!";
        }
	}

}
