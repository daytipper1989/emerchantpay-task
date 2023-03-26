package com.emerchantpay.task.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDto {
	
	private Long id;
	
	private String uuid;
	
	private double amount;
	
	private String status; //(approved, reversed, refunded, error)
	
	private String customerEmail;

	private String customerPhone;

	private TransactionDto reference;
	
	private MerchantDto merchant;
	
}
