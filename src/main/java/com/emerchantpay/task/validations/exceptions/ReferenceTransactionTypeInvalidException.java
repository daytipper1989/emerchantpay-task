package com.emerchantpay.task.validations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Reference transaction type is invalid")
public class ReferenceTransactionTypeInvalidException extends ResponseStatusException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7568567445932415508L;

	public ReferenceTransactionTypeInvalidException() {
		super(HttpStatus.NOT_ACCEPTABLE, "Reference transaction type is invalid");
	}
	
	public ReferenceTransactionTypeInvalidException(HttpStatusCode status, String reason) {
		super(status, reason);
	}

}
