package de.unisaarland.edutech.conceptmapping;

public interface ConceptFactory {

	Concept create(User u);
	Concept create(User u, String s);
	
}
