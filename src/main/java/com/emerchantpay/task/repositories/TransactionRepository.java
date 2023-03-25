package com.emerchantpay.task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emerchantpay.task.models.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
