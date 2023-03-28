package com.emerchantpay.task.validations.interfaces;

import com.emerchantpay.task.validations.exceptions.MerchantDoesNotExistException;
import com.emerchantpay.task.validations.exceptions.MerchantHasTransactionException;

public interface MerchantValidation {
	public void exists(Long id) throws MerchantDoesNotExistException;
	public void hasTransaction(Long id) throws MerchantHasTransactionException;
}
