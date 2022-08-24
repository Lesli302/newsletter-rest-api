package com.newsletter.dao;

import com.newsletter.persistence.entity.User;

public interface UserDAO {
	
	/**
	 * Guarda en BD la informaci&oacute;n de usuario
	 * @param user
	 */
    public void save(User user);
}
