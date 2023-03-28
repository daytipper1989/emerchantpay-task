package com.emerchantpay.task.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emerchantpay.task.models.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	public List<Transaction> findByMerchant_Id(Long merchantId);
	public Optional<Transaction> findByUuid(String uuid);
}
