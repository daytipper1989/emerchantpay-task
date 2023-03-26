package com.emerchantpay.task.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emerchantpay.task.dtos.TransactionDto;
import com.emerchantpay.task.repositories.TransactionRepository;
import com.emerchantpay.task.services.interfaces.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Override
	public List<TransactionDto> getAll() {
		List<TransactionDto> transactions = transactionRepository.findAll().stream().map(model -> {
				TransactionDto transaction = TransactionDto.builder().build();
				BeanUtils.copyProperties(model, transaction);
				return transaction;
			}
		).collect(Collectors.toList());
		
		return transactions;
	}

}
