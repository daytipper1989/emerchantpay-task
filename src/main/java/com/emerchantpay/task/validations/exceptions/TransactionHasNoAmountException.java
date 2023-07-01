package com.emerchantpay.task.validations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Transaction has no amount")
public class TransactionHasNoAmountException extends ResponseStatusException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7347073006559574577L;

	public TransactionHasNoAmountException() {
		super(HttpStatus.NOT_ACCEPTABLE, "Transaction has no amount");
	}
	
	public TransactionHasNoAmountException(HttpStatusCode status, String reason) {
		super(status, reason);
	}

}
