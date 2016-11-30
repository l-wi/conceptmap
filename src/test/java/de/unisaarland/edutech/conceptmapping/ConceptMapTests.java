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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import org.junit.Test;

public class ConceptMapTests {

	@Test
	public void testAddConceptAndCount() {
		// given
		HashMap<String, Concept> concepts = new HashMap<String, Concept>();
		ConceptMap map = createNonLinkedTestMap(concepts);
		// then
		assertSame(6, map.getConceptCount());

	}

	@Test
	public void testIndexOf() {
		// given
		HashMap<String, Concept> concepts = new HashMap<String, Concept>();
		ConceptMap map = createNonLinkedTestMap(concepts);

		// when
		int dogIndex = map.indexOf(concepts.get("dog"));
		int mouseIndex = map.indexOf(concepts.get("mouse"));
		int airplaneIndex = map.indexOf(concepts.get("airplane"));

		// then
		assertSame(0, dogIndex);
		assertSame(2, mouseIndex);
		assertSame(5, airplaneIndex);
	}

	@Test
	public void testLink() {
		// given
		String expected = "		Dog:		Cat:		Mouse:		Food:		Trap:		Airpl.:		\n"
				+ "Dog:		null		Link		null		Link		null		null		\n"
				+ "Cat:		null		null		null		Link		null		null		\n"
				+ "Mouse:		null		Link		null		Link		Link		null		\n"
				+ "Food:		Link		Link		Link		null		null		null		\n"
				+ "Trap:		null		null		null		null		null		null		\n"
				+ "Airpl.:		null		null		null		null		null		null		\n";

		HashMap<String, Concept> concepts = new HashMap<String, Concept>();
		ConceptMap map = createNonLinkedTestMap(concepts);

		// when
		createLinkedMatrix(concepts, map);

		assertEquals(expected, map.toString());

	}

	@Test
	public void testRemove() {
		// given
		String expectedAfterFirstDelete = "		Dog:		Cat:		Mouse:		Airpl.:		Trap:		\n"
				+ "Dog:		null		Link		null		null		null	\t\n"
				+ "Cat:		null		null		null		null		null	\t\n"
				+ "Mouse:		null		Link		null		null		Link	\t\n"
				+ "Airpl.:		null		null		null		null		null	\t\n"
				+ "Trap:		null		null		null		null		null	\t\n";
		HashMap<String, Concept> concepts = new HashMap<String, Concept>();
		ConceptMap map = createNonLinkedTestMap(concepts);

		Concept dog = concepts.get("dog");
		Concept cat = concepts.get("cat");
		createLinkedMatrix(concepts, map);
		;
		// when
		map.removeConcept(concepts.get("food"));
		assertEquals(expectedAfterFirstDelete, map.toString());

		map.removeDirectedLink(dog, cat);
		assertTrue(!map.isLinkedDirected(dog, cat));

		map.removeConcept(dog);
		assertSame(4, map.getConceptCount());

	}

	@Test
	public void testSetDirectedRelationToUndirected() {
		// given
		HashMap<String, Concept> concepts = new HashMap<String, Concept>();
		ConceptMap map = createNonLinkedTestMap(concepts);
		createLinkedMatrix(concepts, map);

		Concept mouse = concepts.get("mouse");
		Concept trap = concepts.get("trap");

		assertTrue(map.isLinkedDirected(mouse, trap));

		// when
		map.setDirectedRelationToUndirected(mouse, trap);

		// then
		assertTrue(map.isLinkedUndirected(mouse, trap));

	}

	private void createLinkedMatrix(HashMap<String, Concept> concepts, ConceptMap map) {
		int dogIndex = map.indexOf(concepts.get("dog"));
		int catIndex = map.indexOf(concepts.get("cat"));
		map.addDirectedLink(dogIndex, catIndex);
		map.addDirectedLink(concepts.get("mouse"), concepts.get("cat"));

		map.addUndirectedLink(concepts.get("mouse"), concepts.get("food"));
		map.addUndirectedLink(concepts.get("dog"), concepts.get("food"));
		map.addUndirectedLink(concepts.get("cat"), concepts.get("food"));

		map.addDirectedLink(concepts.get("mouse"), concepts.get("trap"));
	}

	private ConceptMap createNonLinkedTestMap(HashMap<String, Concept> cm) {
		//
		User tim = new User("tim", "tim@somemail.com");
		FocusQuestion question = new FocusQuestion("test=", tim);

		cm.put("dog", new Concept(new CollaborativeString(tim, "Dog")));
		cm.put("cat", new Concept(new CollaborativeString(tim, "Cat")));
		cm.put("mouse", new Concept(new CollaborativeString(tim, "Mouse")));
		cm.put("food", new Concept(new CollaborativeString(tim, "Food")));
		cm.put("trap", new Concept(new CollaborativeString(tim, "Trap")));
		cm.put("airplane", new Concept(new CollaborativeString(tim, "Airpl.")));

		Experiment experiment = new Experiment(tim, question,4,false,false);
		ConceptMap map = new ConceptMap(experiment);

		map.addConcept(cm.get("dog"));
		map.addConcept(cm.get("cat"));
		map.addConcept(cm.get("mouse"));
		map.addConcept(cm.get("food"));
		map.addConcept(cm.get("trap"));
		map.addConcept(cm.get("airplane"));

		return map;
	}

	@Test
	public void testSerialize() throws IOException, ClassNotFoundException {
		// given
		HashMap<String, Concept> concepts = new HashMap<String, Concept>();
		ConceptMap map = createNonLinkedTestMap(concepts);
		createLinkedMatrix(concepts, map);

		File f = File.createTempFile("test", ".tmp");

		// when
		ObjectOutputStream outputter = new ObjectOutputStream(new FileOutputStream(f));
		ObjectInputStream inputter = new ObjectInputStream(new FileInputStream(f));
		outputter.writeObject(map);
		outputter.close();

		ConceptMap newMap = (ConceptMap) inputter.readObject();

		// then
		assertSame(map.getConceptCount(), newMap.getConceptCount());
		assertEquals(map, newMap);

	}

	@Test
	public void testClone() {
		// given
		HashMap<String, Concept> concepts = new HashMap<String, Concept>();
		ConceptMap map = createNonLinkedTestMap(concepts);
		createLinkedMatrix(concepts, map);

		// when
		ConceptMap map2 = map.clone();

		// then
		assertEquals(map, map2);
		assertNotSame(map, map2);

		map2.removeConcept(concepts.get("dog"));
		assertNotEquals(map, map2);

	}

}
