package com.newsletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication (scanBasePackages = { "com.newsletter.controller", "com.newsletter.service", "com.newsletter.persistence.repository"})
public class NewsletterApirestApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsletterApirestApplication.class, args);
	}

}
