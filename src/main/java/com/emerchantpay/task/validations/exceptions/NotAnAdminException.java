package com.emerchantpay.task.validations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "merchant has no admin role")
public class NotAnAdminException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3024282281342004019L;

}
