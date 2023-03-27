package com.emerchantpay.task.services.interfaces;

import java.util.List;

import com.emerchantpay.task.dtos.TransactionDto;

public interface TransactionService {
	public List<TransactionDto> getAll();
	public TransactionDto apply(TransactionDto transactionDto);
}
