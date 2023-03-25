package com.emerchantpay.task.dtos;

import java.util.List;

import lombok.Data;

@Data
public class Merchant {
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private String email;
	
	private String status; //active or inactive
	
	private double totalTransactionSum;
	
	private List<Transaction> transactions;
	
}
