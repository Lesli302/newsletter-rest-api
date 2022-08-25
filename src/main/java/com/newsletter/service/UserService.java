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
	 * Agrega varios usuarios 
	 * @param file
	 * @return
	 */
	public String addList(MultipartFile file);
}
