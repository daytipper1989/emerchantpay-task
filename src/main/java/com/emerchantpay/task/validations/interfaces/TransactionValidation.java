package com.emerchantpay.task.validations.interfaces;

import com.emerchantpay.task.dtos.TransactionDto;
import com.emerchantpay.task.validations.exceptions.EmailInvalidException;
import com.emerchantpay.task.validations.exceptions.ReferenceTransactionStatusInvalidException;
import com.emerchantpay.task.validations.exceptions.ReferenceTransactionTypeInvalidException;
import com.emerchantpay.task.validations.exceptions.TransactionHasNoAmountException;
import com.emerchantpay.task.validations.exceptions.TransactionUuidAlreadyExistsException;

public interface TransactionValidation {
	public void validateUuid(String uuid) throws TransactionUuidAlreadyExistsException;

	public void validateEmail(String email) throws EmailInvalidException;

	public void validateHasAmount(double amount) throws TransactionHasNoAmountException;

	public void validateReferenceTrasaction(TransactionDto transactionDto)
			throws ReferenceTransactionTypeInvalidException, ReferenceTransactionStatusInvalidException;
}
