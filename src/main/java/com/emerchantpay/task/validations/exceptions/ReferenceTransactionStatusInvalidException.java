package com.emerchantpay.task.validations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Reference transaction status is invalid")
public class ReferenceTransactionStatusInvalidException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5173856392749671601L;

}
