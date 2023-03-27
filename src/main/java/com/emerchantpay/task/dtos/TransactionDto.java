package com.emerchantpay.task.dtos;

import com.emerchantpay.task.dtos.enums.TransactionTypeDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
	
	private Long id;
	
	private String uuid;
	
	private double amount;
	
	private String status; //(approved, reversed, refunded, error)
	
	private String customerEmail;

	private String customerPhone;

	private TransactionDto reference;
	
	private MerchantDto merchant;
	
	private TransactionTypeDto type; //(authorize, charge, refund, reversal)
	
}
