package com.emerchantpay.task.validations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Email is invalid")
public class EmailInvalidException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7019870534134080993L;

}
