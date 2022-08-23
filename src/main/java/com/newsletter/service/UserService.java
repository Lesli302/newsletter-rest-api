package com.newsletter.service;

public interface UserService {

	/**
	 * Agrega un usuario a la lista de destinatarios
	 * @param User objeto con datos del usuario
	 * @return regresa el resultado. OK operaci&oacute;n exitosa, DUP email existente
	 */
	public String save(String email);
}
