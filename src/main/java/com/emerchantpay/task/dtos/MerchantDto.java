package com.emerchantpay.task.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDto {
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private String email;
	
	private String status; //active or inactive
	
	private double totalTransactionSum;
	
	private boolean admin;
	
}
