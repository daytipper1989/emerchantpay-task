package com.emerchantpay.task;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.emerchantpay.task.dtos.MerchantDto;
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
import com.emerchantpay.task.validations.exceptions.ReferenceTransactionStatusInvalidException;
import com.emerchantpay.task.validations.exceptions.ReferenceTransactionTypeInvalidException;
import com.emerchantpay.task.validations.exceptions.TransactionHasNoAmountException;
import com.emerchantpay.task.validations.exceptions.TransactionUuidAlreadyExistsException;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class TaskApplicationTests {

	@MockBean
	private MerchantRepository merchantRepository;
	
	@MockBean
	private TransactionRepository transactionRepository;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private TransactionService transactionService;
	
	@BeforeEach
	public void loadContext() {
		Merchant loggedInUser = new Merchant();
		loggedInUser.setEmail("loggedInUser@email.com");
		loggedInUser.setId(345L);
		loggedInUser.setAdmin(true);
		
		when(merchantRepository.findById(345L)).thenReturn(Optional.ofNullable(loggedInUser));
		
		Optional<Merchant> loggedInOptional = Optional.of(loggedInUser);
		
		
		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.getName()).thenReturn(loggedInUser.getEmail());
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		when(merchantRepository.findById(loggedInUser.getId())).thenReturn(loggedInOptional);
		when(merchantRepository.findByEmail(loggedInUser.getEmail())).thenReturn(loggedInOptional);
		
		when(merchantRepository.findById(100L)).thenReturn(Optional.ofNullable(null));
		
		Merchant merchant = new Merchant();
		merchant.setId(1L);
		when(merchantRepository.findById(1L)).thenReturn(Optional.ofNullable(merchant));
		
		Transaction transaction3 = new Transaction();
		transaction3.setType("CHARGE");
		transaction3.setStatus("approved");
		Transaction transaction4 = new Transaction();
		transaction4.setType("AUTHORIZE");
		transaction4.setStatus("approved");
		Transaction transaction5 = new Transaction();
		transaction5.setType("CHARGE");
		transaction5.setStatus("refunded");
		Transaction transaction6 = new Transaction();
		transaction6.setType("AUTHORIZE");
		transaction6.setStatus("reversed");
		Merchant merchant2 = new Merchant();
		merchant2.setId(2L);
		merchant2.setTotalTransactionSum(100d);
		
		when(merchantRepository.findById(2L)).thenReturn(Optional.ofNullable(merchant2));
		
		when(transactionRepository.findByUuid("UUID1")).thenReturn(Optional.of(transaction3));
		when(transactionRepository.findByUuid("UUID2")).thenReturn(Optional.ofNullable(null));
		when(transactionRepository.findById(3L)).thenReturn(Optional.of(transaction3));
		when(transactionRepository.findById(4L)).thenReturn(Optional.of(transaction4));
		when(transactionRepository.findById(5L)).thenReturn(Optional.of(transaction5));
		when(transactionRepository.findById(6L)).thenReturn(Optional.of(transaction6));
		when(transactionRepository.findByMerchant_Id(merchant2.getId())).thenReturn(Arrays.asList(transaction3));
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction3);
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
	void applyValidAuthorizeTransaction() {
		TransactionDto transaction = TransactionDto.builder()
		.amount(1d)
		.customerEmail("customer@email.com")
		.customerPhone("123456")
		.type(TransactionTypeDto.AUTHORIZE)
		.uuid("UUID2").build();
		
		try {
			transactionService.apply(transaction);
			assertEquals(1d, transaction.getAmount());
			assertEquals("customer@email.com", transaction.getCustomerEmail());
			assertEquals(TransactionTypeDto.AUTHORIZE, transaction.getType());
			assertEquals("UUID2", transaction.getUuid());
		} catch(Exception e) {
			assert(false);
		}
	}
	
	@Test
	void applyValidChargeTransaction() {
		TransactionDto transaction = TransactionDto.builder()
		.amount(1d)
		.customerEmail("customer@email.com")
		.customerPhone("123456")
		.type(TransactionTypeDto.CHARGE)
		.uuid("UUID2")
		.merchant(
					MerchantDto.builder().id(2L).build()
				).build();
		
		try {
			transaction = transactionService.apply(transaction);
			assertEquals(1d, transaction.getAmount());
			assertEquals("customer@email.com", transaction.getCustomerEmail());
			assertEquals(TransactionTypeDto.CHARGE, transaction.getType());
			assertEquals("UUID2", transaction.getUuid());
			assertEquals(101d, transaction.getMerchant().getTotalTransactionSum());
		} catch(Exception e) {
			assert(false);
		}
	}
	
	@Test
	void applyValidRefundTransaction() {
		TransactionDto transaction = TransactionDto.builder()
		.amount(1d)
		.customerEmail("customer@email.com")
		.customerPhone("123456")
		.type(TransactionTypeDto.REFUND)
		.uuid("UUID2")
		.reference(
				TransactionDto.builder().id(3L).build()
				)
		.merchant(
				MerchantDto.builder().id(2L).build()
			).build();
		
		try {
			transactionService.apply(transaction);
			assertEquals(1d, transaction.getAmount());
			assertEquals("customer@email.com", transaction.getCustomerEmail());
			assertEquals(TransactionTypeDto.REFUND, transaction.getType());
			assertEquals("UUID2", transaction.getUuid());
			assertEquals(99d, transaction.getMerchant().getTotalTransactionSum());
			assertEquals("refunded", transaction.getReference().getStatus());
		} catch(Exception e) {
			assert(false);
		}
	}
	
	@Test
	void applyValidReversalTransaction() {
		TransactionDto transaction = TransactionDto.builder()
		.customerEmail("customer@email.com")
		.customerPhone("123456")
		.type(TransactionTypeDto.REVERSAL)
		.uuid("UUID2")
		.reference(
				TransactionDto.builder().id(4L).build()
				).build();
		
		try {
			transactionService.apply(transaction);
			assertEquals("customer@email.com", transaction.getCustomerEmail());
			assertEquals(TransactionTypeDto.REVERSAL, transaction.getType());
			assertEquals("UUID2", transaction.getUuid());
			assertEquals("reversed", transaction.getReference().getStatus());
		} catch(Exception e) {
			assert(false);
		}
	}
	
	@Test
	void applyInvalidAmountAuthorizeTransaction() {
		TransactionDto transaction = TransactionDto.builder()
		.amount(-1d)
		.customerEmail("customer@email.com")
		.customerPhone("123456")
		.type(TransactionTypeDto.AUTHORIZE)
		.uuid("UUID2").build();
		
		try {
			transactionService.apply(transaction);
			assert(false);
		} catch(Exception e) {
			assert(e instanceof TransactionHasNoAmountException);
		}
	}
	
	@Test
	void applyInvalidAmountChargeTransaction() {
		TransactionDto transaction = TransactionDto.builder()
		.amount(-1d)
		.customerEmail("customer@email.com")
		.customerPhone("123456")
		.type(TransactionTypeDto.CHARGE)
		.uuid("UUID2").build();
		
		try {
			transactionService.apply(transaction);
			assert(false);
		} catch(Exception e) {
			assert(e instanceof TransactionHasNoAmountException);
		}
	}
	
	@Test
	void applyInvalidAmountRefundTransaction() {
		TransactionDto transaction = TransactionDto.builder()
		.amount(0)
		.customerEmail("customer@email.com")
		.customerPhone("123456")
		.type(TransactionTypeDto.REFUND)
		.uuid("UUID2").build();
		
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
		.uuid("UUID2").build();
		
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
		.uuid("UUID1").build();
		
		try {
			transactionService.apply(transaction);
			assert(false);
		} catch(Exception e) {
			assert(e instanceof TransactionUuidAlreadyExistsException);
		}
	}
	
	@Test
	void applyInValidTypeReferenceforRefundTransaction() {
		TransactionDto transaction = TransactionDto.builder()
		.amount(1d)
		.customerEmail("customer@email.com")
		.customerPhone("123456")
		.type(TransactionTypeDto.REFUND)
		.uuid("UUID2")
		.reference(
				TransactionDto.builder().id(4L).build()
				)
		.merchant(
				MerchantDto.builder().id(2L).build()
			).build();
		
		try {
			transactionService.apply(transaction);
			assert(false);
		} catch(Exception e) {
			assert(e instanceof ReferenceTransactionTypeInvalidException);
		}
	}
	
	@Test
	void applyInValidTypeReferenceforReversalTransaction() {
		TransactionDto transaction = TransactionDto.builder()
		.customerEmail("customer@email.com")
		.customerPhone("123456")
		.type(TransactionTypeDto.REVERSAL)
		.uuid("UUID2")
		.reference(
				TransactionDto.builder().id(3L).build()
				).build();
		
		try {
			transactionService.apply(transaction);
			assert(false);
		} catch(Exception e) {
			assert(e instanceof ReferenceTransactionTypeInvalidException);
		}
	}
	
	@Test
	void applyInValidStatusReferenceforRefundTransaction() {
		TransactionDto transaction = TransactionDto.builder()
		.amount(1d)
		.customerEmail("customer@email.com")
		.customerPhone("123456")
		.type(TransactionTypeDto.REFUND)
		.uuid("UUID2")
		.reference(
				TransactionDto.builder().id(5L).build()
				)
		.merchant(
				MerchantDto.builder().id(2L).build()
			).build();
		
		try {
			transactionService.apply(transaction);
			assert(false);
		} catch(Exception e) {
			assert(e instanceof ReferenceTransactionStatusInvalidException);
		}
	}
	
	@Test
	void applyInValidStatusReferenceforReversalTransaction() {
		TransactionDto transaction = TransactionDto.builder()
		.customerEmail("customer@email.com")
		.customerPhone("123456")
		.type(TransactionTypeDto.REVERSAL)
		.uuid("UUID2")
		.reference(
				TransactionDto.builder().id(6L).build()
				).build();
		
		try {
			transactionService.apply(transaction);
			assert(false);
		} catch(Exception e) {
			assert(e instanceof ReferenceTransactionStatusInvalidException);
		}
	}
}
