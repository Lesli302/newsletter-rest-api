package com.newsletter.persistence.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection="users")
public class User implements Serializable{

	private static final long serialVersionUID = 8926318522528820547L;

	/**
	 * ID del documento
	 */
	@Id
	private String documentId;
	
	/**
	 * Correo del usuario
	 */
	private String email;
	
}
