package de.unisaarland.edutech.conceptmapping;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FocusQuestion {

	private String question;
	private User creator;
	private Date creationDate;

	public FocusQuestion(String question, User creator) {
		this.question = question;
		this.creator = creator;
		this.creationDate = new Date();
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public User getCreator() {
		return creator;
	}

	public String getQuestion() {
		return question;
	}

	@Override
	public String toString() {
		return creator + "(" + SimpleDateFormat.getInstance().format(creationDate) + ") :" + question;
	}

}
