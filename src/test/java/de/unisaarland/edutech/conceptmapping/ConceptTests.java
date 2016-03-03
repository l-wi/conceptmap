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
