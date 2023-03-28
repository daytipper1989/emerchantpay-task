package com.emerchantpay.task.validations.interfaces;

import com.emerchantpay.task.validations.exceptions.EmailInvalidException;
import com.emerchantpay.task.validations.exceptions.TransactionHasNoAmountException;
import com.emerchantpay.task.validations.exceptions.TransactionStatusInvalidException;
import com.emerchantpay.task.validations.exceptions.TransactionUuidAlreadyExistsException;

public interface TransactionValidation {
	public void validateUuid(String uuid) throws TransactionUuidAlreadyExistsException;
	public void validateEmail(String email) throws EmailInvalidException;
	public void validateHasAmount(double amount) throws TransactionHasNoAmountException;
	public void validateStatus(String status) throws TransactionStatusInvalidException;
}
