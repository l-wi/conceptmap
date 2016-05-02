package de.unisaarland.edutech.conceptmapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Participants {

	private List<User> participants = new ArrayList<>();

	public Participants() {
	}

	public List<User> getParticipants() {
		return Collections.unmodifiableList(participants);
	}

	public void addParticipant(User u) {
		this.participants.add(u);
	}

	public User getTopUser() {
		return participants.get(0);
	}

	public User getBottomUser() {
		return participants.get(1);
	}

	public User getLeftUser() {
		return participants.get(2);
	}

	public User getRightUser() {
		return participants.get(3);
	}

}
