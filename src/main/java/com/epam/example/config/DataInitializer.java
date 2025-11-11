package com.epam.example.config;

import com.epam.example.domain.User;
import com.epam.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) {
		if (userRepository.findByUsername("admin").isEmpty()) {
			User admin = new User();
			admin.setUsername(System.getenv("STATIC_ANALYSIS_ADMIN_USERNAME") != null 
				? System.getenv("STATIC_ANALYSIS_ADMIN_USERNAME") : "default");
			admin.setPassword(System.getenv("STATIC_ANALYSIS_ADMIN_PASSWORD") != null 
				? System.getenv("STATIC_ANALYSIS_ADMIN_PASSWORD") : "default123");
			admin.setEmail(System.getenv("STATIC_ANALYSIS_ADMIN_EMAIL") != null 
				? System.getenv("STATIC_ANALYSIS_ADMIN_EMAIL") : "default@example.com");
			admin.setSecret(System.getenv("STATIC_ANALYSIS_ADMIN_SECRET") != null 
				? System.getenv("STATIC_ANALYSIS_ADMIN_SECRET") : "default-secret-key");
			userRepository.save(admin);
		}
	}
}

