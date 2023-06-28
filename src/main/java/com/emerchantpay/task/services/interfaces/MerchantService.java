package com.emerchantpay.task.services.interfaces;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.emerchantpay.task.dtos.MerchantDto;

public interface MerchantService {
	public List<MerchantDto> getAll();
	public void delete(Long id);
	public MerchantDto update(MerchantDto merchantDto);
	public MerchantDto addAmount(Long id, double amount);
	public MerchantDto subtractAmount(Long id, double amount);
	
	public UserDetailsService userDetailsService();
}
