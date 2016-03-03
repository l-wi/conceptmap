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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caption == null) ? 0 : caption.hashCode());
		result = prime * result + ((u1 == null) ? 0 : u1.hashCode());
		result = prime * result + ((u2 == null) ? 0 : u2.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Link other = (Link) obj;
		if (caption == null) {
			if (other.caption != null)
				return false;
		} else if (!caption.equals(other.caption))
			return false;
		if (u1 == null) {
			if (other.u1 != null)
				return false;
		} else if (!u1.equals(other.u1))
			return false;
		if (u2 == null) {
			if (other.u2 != null)
				return false;
		} else if (!u2.equals(other.u2))
			return false;
		return true;
	}
	
	
}
