package com.newsletter.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UserServiceImpl implements UserService {

	final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired 
	private JavaMailSender javaMailSender;
	 
    @Value("${spring.mail.username}") 
    private String sender;
	
	List<String> emailList=new ArrayList<>();
	
	@Override
    public String save(String email) {
		
		String res="OK";
		//  solo agrega el correo si no existe en la lista
		if (!emailList.contains(email))
			emailList.add(email);
		else
			res="FAIL";
        for(String e:emailList) {
        	logger.info("Correo agregado: {}", e);
        }
        return res;
    }
	
	
	@Override
    public String sendNewsletter(MultipartFile file) {
		try {
			logger.info("Enviando correo.");
			return sendAttachmentEmail(file.getName(), file.getBytes(), emailList);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "";
    }
	
	private String sendAttachmentEmail (String fileName, byte[] file, List<String> destinatarios) {
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
        	logger.error("Fail. {}", e.getMessage());
            return "Error while sending mail!!!";
        }
	}
}
