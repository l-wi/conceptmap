package de.unisaarland.edutech.conceptmapping;

import java.io.Serializable;

public class Link implements Serializable{

	private CollaborativeString caption;
	private User u1;
	private User u2;

	public Link(User u1, User u2, CollaborativeString caption) {
		this.u1 = u1;
		this.u2 = u2;
		this.caption = caption;
	}


	public CollaborativeString getCaption() {
		return caption;
	}
	

	public User getFirstUser() {
		return u1;
	}
	

	public User getSecondUser() {
		return u2;
	}
	@Override
	public String toString() {
		return caption.getContent();
	}


	
}
