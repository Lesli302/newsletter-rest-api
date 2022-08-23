package com.newsletter.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

	final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
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
}
