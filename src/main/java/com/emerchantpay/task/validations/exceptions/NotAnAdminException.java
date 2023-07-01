package com.emerchantpay.task.validations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "merchant has no admin role")
public class NotAnAdminException extends ResponseStatusException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3024282281342004019L;

	public NotAnAdminException() {
		super(HttpStatus.NOT_ACCEPTABLE, "merchant has no admin role");
	}
	
	public NotAnAdminException(HttpStatusCode status, String reason) {
		super(status, reason);
	}
}
