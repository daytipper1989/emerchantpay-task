package com.emerchantpay.task.validations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Transaction UUID already exists")
public class TransactionUuidAlreadyExistsException extends ResponseStatusException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6244003579133573270L;

	public TransactionUuidAlreadyExistsException() {
		super(HttpStatus.NOT_ACCEPTABLE, "Transaction UUID already exists");
	}
	
	public TransactionUuidAlreadyExistsException(HttpStatusCode status, String reason) {
		super(status, reason);
	}
	
}
