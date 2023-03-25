package com.emerchantpay.task.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transaction {
	
	private Long id;
	
	private String uuid;
	
	private double amount;
	
	private String status; //(approved, reversed, refunded, error)
	
	private String customerEmail;

	private String customerPhone;

	private Transaction reference;
	
	private Merchant merchant;
	
}