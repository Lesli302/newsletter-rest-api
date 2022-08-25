package com.newsletter.service;

import org.springframework.web.multipart.MultipartFile;

public interface MailService {
	
	/**
	 * Env&iacute;a el correo a los usuarios con el archivo cargado
	 * @param file
	 * @return
	 */
	public void sendNewsletter(MultipartFile file);
	
	

}
