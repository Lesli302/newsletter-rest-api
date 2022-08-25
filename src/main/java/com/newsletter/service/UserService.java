package com.newsletter.service;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {

	/**
	 * Agrega un usuario a la lista de destinatarios
	 * @param User objeto con datos del usuario
	 * @return regresa el resultado. OK operaci&oacute;n exitosa, DUP email existente
	 */
	public void save(String email);
	
	
	/**
	 * Agrega varios usuarios 
	 * @param file
	 * @return respuesta de servicio
	 */
	public void addList(MultipartFile file);
	
	
	/**
	 * elimina usuario por email
	 * @param email correo a eliminar
	 */
	public void delete(String email);
}
