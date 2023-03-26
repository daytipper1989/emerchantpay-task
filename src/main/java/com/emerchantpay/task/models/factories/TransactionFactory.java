package com.emerchantpay.task.models.factories;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.emerchantpay.task.models.Transaction;

@Component
public class TransactionFactory {
	public Transaction getModel(com.emerchantpay.task.dtos.TransactionDto dto) {
		Transaction transaction = new Transaction();
		BeanUtils .copyProperties(dto, transaction);
		return transaction;
	}
}
