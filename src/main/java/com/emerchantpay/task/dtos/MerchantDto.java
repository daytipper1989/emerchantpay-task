package com.emerchantpay.task.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantDto {
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private String email;
	
	private String status; //active or inactive
	
	private double totalTransactionSum;
	
	private List<TransactionDto> transactions;
	
}
