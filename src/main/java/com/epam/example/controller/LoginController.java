package com.epam.example.controller;

import com.epam.example.domain.User;
import com.epam.example.service.UserService;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String index() {
		return "login";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, Model model) {
		User user = userService.authenticate(username, password);
		if (user != null) {
			model.addAttribute("username", user.getUsername());
			model.addAttribute("email", user.getEmail());
			return "dashboard";
		}
		model.addAttribute("error", "Invalid credentials");
		return "login";
	}

	@GetMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}

	// VULNERABLE ENDPOINT - Text4Shell (CVE-2022-42889)
	// Apache Commons Text 1.9 evaluates ${script:js:...} in StringSubstitutor
	@GetMapping("/api/echo")
	@ResponseBody
	public String echo(@RequestParam String message) {
		// VULNERABLE: createInterpolator() enables script execution
		// Attacker can inject ${script:js:...} to execute JavaScript
		// JavaScript can access Spring application context and beans!
		System.out.println("Echo Message: " + message);
		StringSubstitutor sub = StringSubstitutor.createInterpolator();
		String result = sub.replace("Echo: " + message);
		System.out.println("Result Message: " + result);
		return result;
	}
}

