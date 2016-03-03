package de.unisaarland.edutech.conceptmapping;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FocusQuestion implements Serializable {

	private String question;
	private User creator;
	private Date creationDate;

	public FocusQuestion(String question, User creator) {
		this(question, creator, new Date());
	}

	public FocusQuestion(String question, User creator, Date creation) {
		this.question = question;
		this.creator = creator;
		this.creationDate = creation;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
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
		FocusQuestion other = (FocusQuestion) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}

}
