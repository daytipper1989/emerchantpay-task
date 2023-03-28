package com.emerchantpay.task.validations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Transaction status is invalid")
public class TransactionStatusInvalidException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5298655513826911676L;

}
