package com.emerchantpay.task.validations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Transaction is not found")
public class TransactionNotFoundException extends ResponseStatusException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6553123756377749928L;

	public TransactionNotFoundException() {
		super(HttpStatus.NOT_ACCEPTABLE, "ransaction is not found");
	}
	
	public TransactionNotFoundException(HttpStatusCode status, String reason) {
		super(status, reason);
	}
	
}
