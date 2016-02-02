package de.unisaarland.edutech.conceptmapping;

public class Concept{

	
	private CollaborativeString name;
	
	
	public Concept(CollaborativeString name) {
		this.name = name;
	}
	
	public User getOwner(){
		return name.getOwner();
	};

	public CollaborativeString getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name.getContent();
	}
}
