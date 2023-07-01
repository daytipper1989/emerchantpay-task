package com.emerchantpay.task.validations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Reference transaction status is invalid")
public class ReferenceTransactionStatusInvalidException extends ResponseStatusException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5173856392749671601L;

	public ReferenceTransactionStatusInvalidException() {
		super(HttpStatus.NOT_ACCEPTABLE, "Reference transaction status is invalid");
	}
	
	public ReferenceTransactionStatusInvalidException(HttpStatusCode status, String reason) {
		super(status, reason);
	}
}
