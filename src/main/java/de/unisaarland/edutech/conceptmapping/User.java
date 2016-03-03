package de.unisaarland.edutech.conceptmapping;

import java.io.Serializable;

import org.apache.commons.validator.routines.EmailValidator;

import de.unisaarland.edutech.conceptmapping.exception.EmailException;

public class User implements Serializable {

	private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();
	private String name;
	private String email;

	public User(String name, String email) {
		throwIfInvalidEmail(email);
		this.name = name;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name + "( " + email + " ) ";
	}

	private void throwIfInvalidEmail(String email) {
		if (!EMAIL_VALIDATOR.isValid(email))
			throw new EmailException(email);
	}
	

}
