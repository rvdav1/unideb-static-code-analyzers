package com.epam.example.service;

import com.epam.example.domain.User;
import com.epam.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public User authenticate(String username, String password) {
		// Intentionally simple authentication for demo purposes
		// This method has security issues that tools should detect
		User user = userRepository.findByUsername(username).orElse(null);
		if (user != null && user.getPassword().equals(password)) {
			return user;
		}
		return null;
	}

	public User createUser(String username, String password, String email) {
		// Missing validation - tools should catch this
		User user = new User();
		user.setUsername(username);
		user.setPassword(password); // No password hashing - security issue
		user.setEmail(email);
		return userRepository.save(user);
	}

	// Direct database lookup - used by vulnerable endpoint
	public User findByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

	// Unused method - should be detected by tools
	private void unusedMethod() {
		System.out.println("This method is never called");
	}

	// Method with style issues - Checkstyle should catch these
	public void methodWithStyleIssues(){
		int x=5; // Missing spaces
		String s="test"; // Missing spaces
		if(x>3){ // Missing spaces and braces
			System.out.println("x is greater than 3");
		}
		// Missing default in switch
		int choice = 1;
		switch(choice){
		case 1:
			break;
		case 2:
			break;
		}
	}
}

