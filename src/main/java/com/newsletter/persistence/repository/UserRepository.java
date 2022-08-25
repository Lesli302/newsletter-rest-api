package com.newsletter.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.newsletter.persistence.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
	
	List<User> findByEmail(String email);

}
