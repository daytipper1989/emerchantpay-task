package com.emerchantpay.task.validations.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emerchantpay.task.models.Merchant;
import com.emerchantpay.task.models.Transaction;
import com.emerchantpay.task.repositories.MerchantRepository;
import com.emerchantpay.task.repositories.TransactionRepository;
import com.emerchantpay.task.validations.exceptions.MerchantDoesNotExistException;
import com.emerchantpay.task.validations.exceptions.MerchantHasTransactionException;
import com.emerchantpay.task.validations.interfaces.MerchantValidation;

@Service
public class MerchantValidationImpl implements MerchantValidation{

	@Autowired
	private MerchantRepository merchantRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Override
	public void exists(Long id) throws MerchantDoesNotExistException {
		Optional<Merchant> merchantOptional = merchantRepository.findById(id);
		if(!merchantOptional.isPresent()) {
			throw new MerchantDoesNotExistException();
		}
	}

	@Override
	public void hasTransaction(Long id) throws MerchantHasTransactionException {
		List<Transaction> transactions = transactionRepository.findByMerchant_Id(id);
		
		if(! (transactions== null || transactions.isEmpty())) {
			throw new MerchantHasTransactionException();
		}
	}

}
