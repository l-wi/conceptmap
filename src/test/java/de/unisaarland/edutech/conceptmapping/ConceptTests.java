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

public class ConceptTests {


	@Test
	public void testClone(){
		Concept c1 = new Concept(new CollaborativeString(new User("test","test@test.de")));
		
		//when
		Concept c2 = c1.clone();
		
		//then
		assertEquals(c1,c2);
		assertNotSame(c1, c2);
		assertNotSame(c1.getName(),c2.getName());
	}

}
