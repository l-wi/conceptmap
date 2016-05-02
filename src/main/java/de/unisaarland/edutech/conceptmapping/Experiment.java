package de.unisaarland.edutech.conceptmapping;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Experiment implements Serializable {

	private User reseacher;
	private Date runDate;
	private List<User> participants = new ArrayList<User>();
	private FocusQuestion focusQuestion;
	public final int  USER_COUNT;
	
	public Experiment(User researcher, FocusQuestion question, int numberOfUsers) {
		this(researcher, question, new Date(),numberOfUsers);
	}

	public Experiment(User researcher, FocusQuestion question, Date run,int numberOfUsers) {
		this.USER_COUNT = numberOfUsers;
		this.reseacher = researcher;
		this.runDate = run;
		this.focusQuestion = question;
	}

	public FocusQuestion getFocusQuestion() {
		return focusQuestion;
	}

	public void addParticipant(User u) {
		participants.add(u);
	}

	public List<User> getParticipants() {
		return Collections.unmodifiableList(participants);
	}

	public User getReseacher() {
		return reseacher;
	}

	public Date getRunDate() {
		return runDate;
	}

	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		DateFormat f = SimpleDateFormat.getInstance();
		builder.append("Experiment (").append(f.format(runDate)).append(") conducted by ").append(reseacher)
				.append(" with participants ");

		for (User u : participants)
			builder.append(u + " ");

		builder.append(" question is: ").append(focusQuestion.getQuestion());

		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((focusQuestion == null) ? 0 : focusQuestion.hashCode());
		result = prime * result + ((participants == null) ? 0 : participants.hashCode());
		result = prime * result + ((reseacher == null) ? 0 : reseacher.hashCode());
		result = prime * result + ((runDate == null) ? 0 : runDate.hashCode());
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
		Experiment other = (Experiment) obj;
		if (focusQuestion == null) {
			if (other.focusQuestion != null)
				return false;
		} else if (!focusQuestion.equals(other.focusQuestion))
			return false;
		if (participants == null) {
			if (other.participants != null)
				return false;
		} else if (!participants.equals(other.participants))
			return false;
		if (reseacher == null) {
			if (other.reseacher != null)
				return false;
		} else if (!reseacher.equals(other.reseacher))
			return false;
		if (runDate == null) {
			if (other.runDate != null)
				return false;
		} else if (!runDate.equals(other.runDate))
			return false;
		return true;
	}

	
}
