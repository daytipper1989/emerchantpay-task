package com.emerchantpay.task.validations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Reference transaction type is invalid")
public class ReferenceTransactionTypeInvalidException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7568567445932415508L;

}
