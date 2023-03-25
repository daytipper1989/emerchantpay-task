package com.emerchantpay.task.models.factories;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.emerchantpay.task.models.Merchant;

@Component
public class MerchantFactory {
	public Merchant getModel(com.emerchantpay.task.dtos.Merchant dto) {
		Merchant merchant = new Merchant();
		BeanUtils .copyProperties(dto, merchant);
		return merchant;
	}
}
