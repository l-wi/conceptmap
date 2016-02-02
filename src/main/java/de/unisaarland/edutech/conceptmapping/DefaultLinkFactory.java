package de.unisaarland.edutech.conceptmapping;

public class DefaultLinkFactory implements LinkFactory {

	public Link create(User u1, User u2) {
		
		return new Link(u1, u2,new CollaborativeString(u1));
		 
		
	}

	
}
