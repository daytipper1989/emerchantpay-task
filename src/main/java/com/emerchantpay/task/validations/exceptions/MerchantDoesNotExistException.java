package com.emerchantpay.task.validations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "merchant not found")
public class MerchantDoesNotExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8111199612777329462L;
	
}
