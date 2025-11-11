package com.epam.example.util;

// This class has issues that tools should detect
public class UtilityClass {
	// Missing private constructor for utility class
	public static void doSomething() {
		// This method is not covered by tests
		System.out.println("Utility method");
	}

	// Unused public method
	public static void unusedPublicMethod() {
		// This will be detected as unused
	}
}

