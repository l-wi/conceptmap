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
