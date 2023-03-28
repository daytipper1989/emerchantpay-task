package com.emerchantpay.task.services.implementations;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emerchantpay.task.dtos.MerchantDto;
import com.emerchantpay.task.dtos.TransactionDto;
import com.emerchantpay.task.dtos.enums.TransactionTypeDto;
import com.emerchantpay.task.models.Merchant;
import com.emerchantpay.task.models.Transaction;
import com.emerchantpay.task.models.factories.TransactionFactory;
import com.emerchantpay.task.repositories.TransactionRepository;
import com.emerchantpay.task.services.interfaces.MerchantService;
import com.emerchantpay.task.services.interfaces.TransactionService;
import com.emerchantpay.task.validations.interfaces.TransactionValidation;

@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	private TransactionFactory transactionFactory;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private TransactionValidation transactionValidation;
	
	@Autowired
	private MerchantService merchantService;
	
	@Value("${transaction.status.approved}")
	private String approvedTransactionStatus;
	
	@Value("${transaction.status.refunded}")
	private String refundedTransactionStatus;
	
	@Value("${transaction.status.reversed}")
	private String reversedTransactionStatus;
	
	@Value("${transaction.status.error}")
	private String errorTransactionStatus;
	
	
	@Override
	public List<TransactionDto> getAll() {
		List<TransactionDto> transactions = transactionRepository.findAll().stream().map(model -> {
				TransactionDto transaction = TransactionDto.builder().build();
				BeanUtils.copyProperties(model, transaction);
				if(model.getType() != null) {
					transaction.setType(TransactionTypeDto.valueOf(model.getType()));
				}
				if(model.getMerchant() != null) {
					MerchantDto merchant = new MerchantDto();
					BeanUtils.copyProperties(model.getMerchant(), merchant);
					transaction.setMerchant(merchant);
				}
				return transaction;
			}
		).collect(Collectors.toList());
		
		return transactions;
	}
	
	@Override
	@Transactional
	public TransactionDto apply(TransactionDto transactionDto) {
		TransactionTypeDto type = transactionDto.getType();
		
		transactionValidation.validateEmail(transactionDto.getCustomerEmail());
		transactionValidation.validateUuid(transactionDto.getUuid());
		
		if(type == TransactionTypeDto.AUTHORIZE || type == TransactionTypeDto.CHARGE || type == TransactionTypeDto.REFUND) {
			transactionValidation.validateHasAmount(transactionDto.getAmount());
		}
		else {
			transactionDto.setAmount(0.0d);
		}
		
		Transaction transaction = transactionFactory.getModel(transactionDto);
		transaction.setType(transactionDto.getType().toString());
		transaction.setStatus(approvedTransactionStatus);
		Merchant merchant = new Merchant();
		if(transactionDto.getMerchant() != null) {
			merchant.setId(transactionDto.getMerchant().getId());
		}
		transaction.setMerchant(merchant);
		transactionRepository.save(transaction);
		
		
		MerchantDto merchantDto = null;
		
		if( transactionDto.getType() == TransactionTypeDto.CHARGE) {
			if(transaction.getMerchant() != null) {
				merchantDto = merchantService.addAmount(transaction.getMerchant().getId(), transaction.getAmount());
			}
		}
		else if( transactionDto.getType() == TransactionTypeDto.REFUND) {
			if(transaction.getMerchant() != null) {
				merchantDto = merchantService.subtractAmount(transaction.getMerchant().getId(), transaction.getAmount());
				
				updateReferenceTransactionStatus(transactionDto.getReference(), refundedTransactionStatus);
			}
		}
		else if( transactionDto.getType() == TransactionTypeDto.REVERSAL) {
			updateReferenceTransactionStatus(transactionDto.getReference(), reversedTransactionStatus);
		}
		
		BeanUtils.copyProperties(transaction, transactionDto);
		transactionDto.setMerchant(merchantDto);
		
		return transactionDto;
	}
	
	@Transactional
	private void updateReferenceTransactionStatus(TransactionDto transactionDto, String status) {
		
		Objects.requireNonNull(transactionDto);
		
		Long id = transactionDto.getId();
		Optional<Transaction> transactionOptional = transactionRepository.findById(id);
		if(transactionOptional.isPresent()) {
			Transaction transaction = transactionOptional.get();
			transaction.setStatus(status);
			transactionRepository.save(transaction);
		}
		else {
			// TODO
			//throw new TransactionRefernceNotFoundException(); 
		}
	}

	@Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
	public void scheduleFixedRateTask() {
	    //System.out.println("Fixed rate task - " + System.currentTimeMillis() / 1000);
	}
}
