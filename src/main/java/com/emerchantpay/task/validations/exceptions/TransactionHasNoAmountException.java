package com.emerchantpay.task.validations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Transaction has no amount")
public class TransactionHasNoAmountException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7347073006559574577L;

}
