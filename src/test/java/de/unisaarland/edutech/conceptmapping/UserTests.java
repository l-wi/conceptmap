/*******************************************************************************
 * conceptmap a concept mapping datastructure used in conceptmap-fx.
 * Copyright (C) Tim Steuer (master's thesis 2016)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, US
 *******************************************************************************/
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
