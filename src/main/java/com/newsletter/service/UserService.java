package com.newsletter.service;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {

	/**
	 * Agrega un usuario a la lista de destinatarios
	 * @param User objeto con datos del usuario
	 * @return regresa el resultado. OK operaci&oacute;n exitosa, DUP email existente
	 */
	public String save(String email);
	
	
	/**
	 * Env&iacute;a el correo a los usuarios con el archivo cargado
	 * @param file
	 * @return
	 */
	public String sendNewsletter(MultipartFile file);
	
	
	/**
	 * Agrega varios usuarios 
	 * @param file
	 * @return
	 */
	public String addList(MultipartFile file);
}
