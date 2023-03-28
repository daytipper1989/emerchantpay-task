package com.emerchantpay.task.services.interfaces;

import java.util.List;

import com.emerchantpay.task.dtos.MerchantDto;
import com.emerchantpay.task.validations.exceptions.MerchantDoesNotExistException;
import com.emerchantpay.task.validations.exceptions.MerchantHasTransactionException;

public interface MerchantService {
	public List<MerchantDto> getAll();
	public void delete(Long id) throws MerchantDoesNotExistException, MerchantHasTransactionException;
	public MerchantDto update(MerchantDto merchantDto);
}
