package com.epam.example.controller;

import com.epam.example.domain.User;
import com.epam.example.service.UserService;
import org.springframework.ui.Model;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class LoginControllerTest {
	private LoginController loginController;
	private UserService userService;
	private Model model;

	@BeforeMethod
	public void setUp() throws Exception {
		userService = mock(UserService.class);
		model = mock(Model.class);
		loginController = new LoginController();
		// Inject mock using reflection
		Field serviceField = LoginController.class.getDeclaredField("userService");
		serviceField.setAccessible(true);
		serviceField.set(loginController, userService);
	}

	@Test
	public void testLoginPage() {
		String view = loginController.loginPage();
		assertEquals(view, "login");
	}

	@Test
	public void testLoginSuccess() {
		User user = new User(1L, "admin", "admin123", "admin@example.com", "secret");
		when(userService.authenticate("admin", "admin123")).thenReturn(user);

		String view = loginController.login("admin", "admin123", model);
		assertEquals(view, "dashboard");
		verify(model).addAttribute("username", "admin");
		verify(model).addAttribute("email", "admin@example.com");
	}

	@Test
	public void testLoginFailure() {
		when(userService.authenticate("admin", "wrongpassword")).thenReturn(null);
		String view = loginController.login("admin", "wrongpassword", model);
		assertEquals(view, "login");
		verify(model).addAttribute("error", "Invalid credentials");
	}
}

