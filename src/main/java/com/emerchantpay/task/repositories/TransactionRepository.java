package com.emerchantpay.task.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emerchantpay.task.models.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	public List<Transaction> findByMerchant_Id(Long merchantId);
	public Optional<Transaction> findByUuid(String uuid);
	
	@Query("select a from Transaction a where a.creationDateTime <= :creationDateTime")
	public List<Transaction> findAllWithCreationDateTimeBefore(@Param("creationDateTime") Date creationDateTime);
}
