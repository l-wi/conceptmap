package de.unisaarland.edutech.conceptmapping;

import java.io.Serializable;

public interface LinkFactory extends Serializable{

	Link create(User u1, User u2);
}
