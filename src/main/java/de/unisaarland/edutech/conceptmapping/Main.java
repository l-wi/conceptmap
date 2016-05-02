package de.unisaarland.edutech.conceptmapping;


public class Main {

	public static void main(String[] args) {
		//user setup first
		User researcher = new User("Armin", "armin@uds.de");
		
		User p1 = new User("Tim", "tim@uds.de");
		User p2 = new User("Luka", "luka@uds.de");
		User p3 = new User("Max", "max@uds.de");
		User p4 = new User("Karo", "karo@uds.de");
		
		//experiment setup
		FocusQuestion question = new FocusQuestion("How do geese fly?", researcher);
		Experiment experiment = new Experiment(researcher, question,4);
		
		
		
		//mapping
		ConceptMap map = new ConceptMap(experiment);
		
		map.addConcept(new Concept(new CollaborativeString(p2,"wings")));
		map.addConcept(new Concept(new CollaborativeString(p1,"physics")));
		map.addConcept(new Concept(new CollaborativeString(p3,"air")));
		
		map.addConcept(new Concept(new CollaborativeString(p4,"bird")));
		map.addDirectedLink(2, 3).getCaption().append(p1, "flies in" );
		map.addConcept(new Concept(new CollaborativeString(p3,"velocity")));
	}
}
