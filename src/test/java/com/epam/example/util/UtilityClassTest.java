package com.epam.example.util;

import org.testng.annotations.Test;

import java.lang.reflect.Constructor;

import static org.testng.Assert.assertNotNull;

public class UtilityClassTest {

	@Test
	public void testDoSomething() {
		// Test the static method
		UtilityClass.doSomething();
	}

	@Test
	public void testUnusedPublicMethod() {
		// Test the unused public method to achieve coverage
		UtilityClass.unusedPublicMethod();
	}

	@Test
	public void testPrivateConstructor() throws Exception {
		// Test that we can instantiate via reflection (for coverage)
		// even though this is a utility class that shouldn't be instantiated
		Constructor<UtilityClass> constructor = UtilityClass.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		
		UtilityClass instance = constructor.newInstance();
		assertNotNull(instance);
	}
}
