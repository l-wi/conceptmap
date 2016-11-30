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

import java.io.Serializable;

import org.apache.commons.validator.routines.EmailValidator;

import de.unisaarland.edutech.conceptmapping.exception.EmailException;

public class User implements Serializable {

	private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();
	private String name;
	private String email;
	private String prompt;

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

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	
	public String getPrompt() {
		return prompt;
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
