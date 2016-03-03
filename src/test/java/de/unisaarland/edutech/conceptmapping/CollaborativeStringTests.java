package de.unisaarland.edutech.conceptmapping;

import static org.junit.Assert.*;

import org.junit.Test;

public class CollaborativeStringTests {

	@Test
	public void testConstructAndGets() {
		// given
		String empty = "";
		String test = "test";

		User u1 = new User("Tim", "some@email.de");
		User u2 = new User("Karsten", "karsten@email.de");

		// when
		CollaborativeString s1 = new CollaborativeString(u1, empty);
		CollaborativeString s2 = new CollaborativeString(u2, test);

		// then

		assertEquals(empty, s1.getContent());
		assertEquals(test, s2.getContent());
		assertEquals(u1, s1.getOwner());
		assertEquals(u2, s2.getOwner());
	}

	@Test
	public void testRemoveAndRemoveLast() {
		// given
		User u1 = new User("Tim", "ingo@email.de");
		User u2 = new User("Kerstin", "tim@email.de");
		User u3 = new User("Frank", "frank@email.de");
		User u4 = new User("Ingo", "kerstin@email.de");

		CollaborativeString s1 = new CollaborativeString(u1, "this ");
		s1.append(u2, "is ");
		s1.append(u3, "a ");
		s1.append(u4, "test");

		// when

		// case: a part of a users region
		s1.remove(0, 2);
		assertEquals("is is a test", s1.getContent());
		assertEquals("<Tim>is </Tim><Kerstin>is </Kerstin><Frank>a </Frank><Ingo>test</Ingo>", s1.toDebugString());

		// case: complete entry of an user
		s1.remove(3, 3);
		assertEquals("is a test", s1.getContent());
		assertEquals("<Tim>is </Tim><Frank>a </Frank><Ingo>test</Ingo>", s1.toDebugString());

		// case: over boundary
		s1.remove(0, s1.length() - 1);
		assertEquals("t", s1.getContent());
		assertEquals("<Ingo>t</Ingo>", s1.toDebugString());
	}

	@Test
	public void testInsertAndAppend() {
		// given
		User tim = new User("Tim", "some@email.de");
		User kerstin = new User("Kerstin", "some@email.de");
		String empty = "";
		String hello = "hello ";
		String my = "my ";
		String pets = " pets";
		String myPets = "my pets ";
		String name = "name ";
		String is = "is ";
		String dolin = "Dolin";
		String ph = "ph";

		CollaborativeString s1 = new CollaborativeString(tim, empty);

		// case empty string
		s1.append(tim, my);
		assertEquals(my, s1.getContent());
		assertEquals("<Tim>my </Tim>", s1.toDebugString());
		assertSame(my.length(), s1.length());

		// case insert front
		s1.insert(kerstin, 0, hello);
		assertEquals(hello + my, s1.getContent());
		assertSame(hello.length() + my.length(), s1.length());

		// case append
		s1.append(tim, is);
		assertEquals(hello + my + is, s1.getContent());
		assertSame(hello.length() + my.length() + is.length(), s1.length());

		// case split word
		s1.insert(kerstin, (hello + my).length() - 1, pets);
		assertEquals(hello + myPets + is, s1.getContent());
		assertSame((hello + myPets + is).length(), s1.length());
		assertEquals("<Kerstin>hello </Kerstin><Tim>my</Tim><Kerstin> pets</Kerstin><Tim> </Tim><Tim>is </Tim>",
				s1.toDebugString());

		// case insert between word boundaries
		s1.insert(tim, s1.length() - is.length(), name);
		assertEquals(hello + myPets + name + is, s1.getContent());
		assertSame(hello.length() + myPets.length() + name.length() + is.length(), s1.length());

		// case append
		s1.append(tim, dolin);
		assertEquals(hello + myPets + name + is + dolin, s1.getContent());
		assertSame(hello.length() + myPets.length() + name.length() + is.length() + dolin.length(), s1.length());

		// case split word last word
		s1.insert(kerstin, s1.length() - 2, ph);
		assertEquals(hello + myPets + name + is + "Dolphin", s1.getContent());
		assertSame((hello + myPets + name + is + "Dolphin").length(), s1.length());
		assertEquals(
				"<Kerstin>hello </Kerstin><Tim>my</Tim><Kerstin> pets</Kerstin><Tim> </Tim><Tim>name </Tim><Tim>is </Tim><Tim>Dol</Tim><Kerstin>ph</Kerstin><Tim>in</Tim>",
				s1.toDebugString());

	}
	
	@Test
	public void testClone(){
		//given
		User u4 = new User("Ingo", "kerstin@email.de");

		CollaborativeString s1 = new CollaborativeString(u4, "this ");
		
		//when
		CollaborativeString s2 = s1.clone();
		
		//then
		assertEquals(s1, s2);
		s2.append(u4, " is");
		assertNotEquals(s1, s2);
	}

}
