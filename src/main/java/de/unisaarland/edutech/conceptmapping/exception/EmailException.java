package de.unisaarland.edutech.conceptmapping.exception;

public class EmailException extends RuntimeException {

	public EmailException(String email) {
		super("Invalid Email Address Specified:\t" + email);
	}
}
