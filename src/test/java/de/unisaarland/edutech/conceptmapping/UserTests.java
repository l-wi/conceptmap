package de.unisaarland.edutech.conceptmapping;

import static org.junit.Assert.*;

import org.junit.Test;

import de.unisaarland.edutech.conceptmapping.exception.EmailException;

public class UserTests {

	@Test
	public void testCreate() {
		// given
		String name = "Tim";
		String email = "tim@somedomain.com";

		// when
		User user = new User(name, email);

		assert (user.getName().equals(name));
		assert (user.getEmail().equals(email));
	}

	@Test
	public void testCreateEmailLongTLD() {
		// given
		String name = "Tim";
		String email = "tim@uds.saarland";

		// when
		new User(name, email);
	}

	@Test(expected = EmailException.class)
	public void testCreateEmailNoTLD() {
		// given
		String name = "Tim";
		String email = "tim@test";

		// when
		new User(name, email);
	}

	@Test(expected = EmailException.class)
	public void testCreateEmailNoAt() {
		// given
		String name = "Tim";
		String email = "tim.de";

		// when
		new User(name, email);
	}

	@Test
	public void testEquals() {
		// given
		User u1 = new User("Tony", "t@uds.de");
		User u2 = new User("Tim", "t@uds.de");
		User u3 = new User("Ina", "i@uds.de");

		assertEquals(u1, u2);
		assertEquals(u2, u1);
		assertNotEquals(u1, u3);
	}
}
