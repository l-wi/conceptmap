package de.unisaarland.edutech.conceptmapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Experiment {

	private User reseacher;
	private Date runDate;
	private List<User> participants = new ArrayList<User>();
	private FocusQuestion focusQuestion;

	public Experiment(User researcher, FocusQuestion question) {
		this.reseacher = researcher;
		this.runDate = new Date();
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

}
