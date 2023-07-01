package com.emerchantpay.task.validations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "merchant has one or more transactions")
public class MerchantHasTransactionException extends ResponseStatusException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7414846544631882386L;
	
	public MerchantHasTransactionException() {
		super(HttpStatus.NOT_ACCEPTABLE, "merchant has one or more transactions");
	}
	
	public MerchantHasTransactionException(HttpStatusCode status, String reason) {
		super(status, reason);
	}
}
