package com.emerchantpay.task.validations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "merchant not found")
public class MerchantDoesNotExistException extends ResponseStatusException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8111199612777329462L;
	
	public MerchantDoesNotExistException() {
		super(HttpStatus.NOT_FOUND, "merchant not found");
	}
	
	public MerchantDoesNotExistException(HttpStatusCode status, String reason) {
		super(status, reason);
	}
}
