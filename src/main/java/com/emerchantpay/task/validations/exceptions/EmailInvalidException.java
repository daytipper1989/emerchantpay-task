package com.emerchantpay.task.validations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Email is invalid")
public class EmailInvalidException extends ResponseStatusException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7019870534134080993L;
	
	public EmailInvalidException() {
		super(HttpStatus.NOT_ACCEPTABLE, "Email is invalid");
	}
	
	public EmailInvalidException(HttpStatusCode status, String reason) {
		super(status, reason);
	}
}
