package com.emerchantpay.task.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "merchants")
@Setter
@Getter
public class Merchant {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String description;
	
	@Column(unique=true)
	private String email;
	
	private String status; //active or inactive
	
	@Column(name="total_transaction_sum")
	private double totalTransactionSum;
	
}
