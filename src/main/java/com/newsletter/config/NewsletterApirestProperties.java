package com.newsletter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("newsletter.rest.api")
public class NewsletterApirestProperties {
	
	private String[] allowedOrigins;

}
