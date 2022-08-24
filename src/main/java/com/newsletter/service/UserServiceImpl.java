package com.newsletter.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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


@Service
public class UserServiceImpl implements UserService {

	final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired 
	private JavaMailSender javaMailSender;
	 
    @Value("${spring.mail.username}") 
    private String sender;
    
    @Value("${spring.mail.password}") 
    private String pass;
    
    private Session session;

	
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
			InputStream initialStream = file.getInputStream();
			return sendAttachmentEmail(file.getName(), initialStream.readAllBytes(), emailList);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "";
    }
	
	@Override
    public String addList(MultipartFile file) {
		try {
			if (!file.isEmpty()) {
				logger.info("archivo no vacio.");
				InputStream initialStream = file.getInputStream();
				Scanner obj = new Scanner(initialStream);
			    while (obj.hasNextLine())
			    	logger.info("Correos: {}", (obj.nextLine()));
			   
			} else {
			      return "You failed to upload " + file.getName() + " because the file was empty.";
			}
	        	
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
            mimeMessageHelper.setText("Te enviamos la noticia de hoy. Para no recibir mas correos da click en el siguiente link: ");
            mimeMessageHelper.setSubject("Newsletter");

            Tika tika = new Tika();
            String mimeType = tika.detect(file);
            logger.info("mimeType. {}", mimeType);
            DataSource datasource;
            if (mimeType.equals("application/pdf"))
            	datasource = new ByteArrayDataSource(file, mimeType);
            else {
            	BodyPart imagen = new MimeBodyPart();
            	datasource = new ByteArrayDataSource(file, "text/html");
            }
            mimeMessageHelper.addAttachment(fileName, datasource);
 
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
