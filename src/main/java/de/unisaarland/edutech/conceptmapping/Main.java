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
		Experiment experiment = new Experiment(researcher, question,4,false,false);
		
		
		
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
