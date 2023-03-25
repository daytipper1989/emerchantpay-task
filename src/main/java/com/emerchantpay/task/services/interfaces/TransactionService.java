package com.emerchantpay.task.services.interfaces;

import java.util.List;

import com.emerchantpay.task.dtos.Transaction;

public interface TransactionService {
	public List<Transaction> getAll();
}
