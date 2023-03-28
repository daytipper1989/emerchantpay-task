package com.emerchantpay.task;


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
import org.springframework.boot.test.mock.mockito.MockBean;

import com.emerchantpay.task.models.Merchant;
import com.emerchantpay.task.models.Transaction;
import com.emerchantpay.task.repositories.MerchantRepository;
import com.emerchantpay.task.services.interfaces.MerchantService;
import com.emerchantpay.task.validations.exceptions.MerchantDoesNotExistException;
import com.emerchantpay.task.validations.exceptions.MerchantHasTransactionException;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class TaskApplicationTests {

	@Mock
	private MerchantRepository merchantRepository;
	
	@Autowired
	private MerchantService merchantService;
	
	@BeforeAll
	public void loadContext() {
		when(merchantRepository.findById(100L)).thenReturn(Optional.ofNullable(null));
		
		Merchant merchant = new Merchant();
		merchant.setId(1L);
		when(merchantRepository.findById(1L)).thenReturn(Optional.ofNullable(merchant));
		
		Transaction transaction = new Transaction();
		Merchant merchant2 = new Merchant();
		merchant2.setId(2L);
		merchant2.setTransactions(Arrays.asList(transaction));
		when(merchantRepository.findById(2L)).thenReturn(Optional.ofNullable(merchant2));
	}
	
	@Test
	void destroyMerchant() {
		try {
			merchantService.delete(1L);
			assert(true);
		} catch (MerchantDoesNotExistException e) {
			assert(false);
		} catch (MerchantHasTransactionException e) {
			assert(false);
		}
	}
	
	@Test
	void destroyInexistingMerchant() {
		try {
			merchantService.delete(100L);
			assert(false);
		} catch (MerchantDoesNotExistException e) {
			assert(true);
		} catch (MerchantHasTransactionException e) {
			assert(false);
		}
	}

	@Test
	void destroyMerchantWhoHasTranaction() {
		try {
			merchantService.delete(2L);
			assert(false);
		} catch (MerchantHasTransactionException e) {
			assert(true);
		}
		catch(MerchantDoesNotExistException e) {
			assert(false);
		}
	}
}
