package com.emerchantpay.task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emerchantpay.task.models.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

}
