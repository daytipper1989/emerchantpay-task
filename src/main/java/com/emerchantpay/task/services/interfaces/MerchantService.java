package com.emerchantpay.task.services.interfaces;

import java.util.List;

import com.emerchantpay.task.dtos.MerchantDto;

public interface MerchantService {
	public List<MerchantDto> getAll();
	public void delete(Long id);
	public MerchantDto update(MerchantDto merchantDto);
}
