package com.emerchantpay.task.validations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "merchant has one or more transactions")
public class MerchantHasTransactionException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7414846544631882386L;

}
