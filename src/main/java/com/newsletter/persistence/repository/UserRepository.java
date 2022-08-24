package com.newsletter.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.newsletter.persistence.entity.User;

public interface UserRepository extends MongoRepository<User,String> {

}
