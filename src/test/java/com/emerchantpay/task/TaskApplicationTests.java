package com.emerchantpay.task;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.emerchantpay.task.dtos.TransactionDto;
import com.emerchantpay.task.dtos.enums.TransactionTypeDto;
import com.emerchantpay.task.models.Merchant;
import com.emerchantpay.task.models.Transaction;
import com.emerchantpay.task.repositories.MerchantRepository;
import com.emerchantpay.task.repositories.TransactionRepository;
import com.emerchantpay.task.services.interfaces.MerchantService;
import com.emerchantpay.task.services.interfaces.TransactionService;
import com.emerchantpay.task.validations.exceptions.EmailInvalidException;
import com.emerchantpay.task.validations.exceptions.MerchantDoesNotExistException;
import com.emerchantpay.task.validations.exceptions.MerchantHasTransactionException;
import com.emerchantpay.task.validations.exceptions.TransactionHasNoAmountException;
import com.emerchantpay.task.validations.exceptions.TransactionUuidAlreadyExistsException;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class TaskApplicationTests {

	@Mock
	private MerchantRepository merchantRepository;
	
	@Mock
	private TransactionRepository transactionRepository;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private TransactionService transactionService;
	
	@BeforeAll
	public void loadContext() {
		when(merchantRepository.findById(100L)).thenReturn(Optional.ofNullable(null));
		
		Merchant merchant = new Merchant();
		merchant.setId(1L);
		when(merchantRepository.findById(1L)).thenReturn(Optional.ofNullable(merchant));
		
		Transaction transaction = new Transaction();
		Merchant merchant2 = new Merchant();
		merchant2.setId(2L);
		//merchant2.setTransactions(Arrays.asList(transaction));
		when(merchantRepository.findById(2L)).thenReturn(Optional.ofNullable(merchant2));
		
		when(transactionRepository.findByUuid("UUID1")).thenReturn(Optional.of(transaction));
		when(transactionRepository.findByUuid("UUID2")).thenReturn(Optional.ofNullable(null));
		
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
	}

	@Test
	void destroyMerchant() {
		try {
			merchantService.delete(1L);
			assert(true);
		} catch (Exception e) {
			assert(false);
		}
	}
	
	@Test
	void destroyInexistingMerchant() {
		try {
			merchantService.delete(100L);
			assert(false);
		} catch (Exception e) {
			assert(e instanceof MerchantDoesNotExistException);
		}
	}

	@Test
	void destroyMerchantWhoHasTranaction() {
		try {
			merchantService.delete(2L);
			assert(false);
		} catch (Exception e) {
			assert(e instanceof MerchantHasTransactionException);
		}
	}
	
	@Test
	void applyTransaction() {
		TransactionDto transaction = TransactionDto.builder()
		.amount(1d)
		.customerEmail("customer@email.com")
		.customerPhone("123456")
		.type(TransactionTypeDto.AUTHORIZE)
		.uuid("UUID1").build();
		
		try {
			transactionService.apply(transaction);
		} catch(Exception e) {
			assert(false);
		}
	}
	
	@Test
	void applyInvalidAmountTransaction() {
		TransactionDto transaction = TransactionDto.builder()
		.amount(-1d)
		.customerEmail("customer@email.com")
		.customerPhone("123456")
		.type(TransactionTypeDto.AUTHORIZE)
		.uuid("UUID1").build();
		
		try {
			transactionService.apply(transaction);
			assert(false);
		} catch(Exception e) {
			assert(e instanceof TransactionHasNoAmountException);
		}
	}
	
	@Test
	void applyInvalidEmailTransaction() {
		TransactionDto transaction = TransactionDto.builder()
		.amount(1d)
		.customerEmail("customer_at_email.com")
		.customerPhone("123456")
		.type(TransactionTypeDto.AUTHORIZE)
		.uuid("UUID1").build();
		
		try {
			transactionService.apply(transaction);
			assert(false);
		} catch(Exception e) {
			assert(e instanceof EmailInvalidException);
		}
	}
	
	@Test
	void applyInvalidUuidTransaction() {
		TransactionDto transaction = TransactionDto.builder()
		.amount(1d)
		.customerEmail("customer@email.com")
		.customerPhone("123456")
		.type(TransactionTypeDto.AUTHORIZE)
		.uuid("UUID2").build();
		
		try {
			transactionService.apply(transaction);
			assert(false);
		} catch(Exception e) {
			e.printStackTrace();
			assert(e instanceof TransactionUuidAlreadyExistsException);
		}
	}
}
