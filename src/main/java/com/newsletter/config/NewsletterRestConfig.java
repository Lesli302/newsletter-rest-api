package com.newsletter.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(NewsletterApirestProperties.class)
public class NewsletterRestConfig {

	@Bean
	public WebMvcConfigurer corsConfigurer(NewsletterApirestProperties newsletterApirestProperties) {
		return new CustomWebMvcConfigurer(newsletterApirestProperties);
	}
	
	private static class CustomWebMvcConfigurer implements WebMvcConfigurer {
		private NewsletterApirestProperties newsletterApirestProperties;
		
		private CustomWebMvcConfigurer (NewsletterApirestProperties newsletterApirestProperties) {
			super();
			this.newsletterApirestProperties=newsletterApirestProperties;
		}
		
		@Override
		public void addCorsMappings (CorsRegistry registry) {
			WebMvcConfigurer.super.addCorsMappings(registry);
			registry.addMapping("/**")
			.allowedMethods("*")
			.allowedOrigins(newsletterApirestProperties.getAllowedOrigins());
		}
	}
}
