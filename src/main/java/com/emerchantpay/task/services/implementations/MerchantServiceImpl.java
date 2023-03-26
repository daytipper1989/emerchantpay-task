package com.emerchantpay.task.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emerchantpay.task.dtos.MerchantDto;
import com.emerchantpay.task.repositories.MerchantRepository;
import com.emerchantpay.task.services.interfaces.MerchantService;

@Service
public class MerchantServiceImpl implements MerchantService {

	@Autowired
	private MerchantRepository merchantRepository;
	
	@Override
	public List<MerchantDto> getAll() {
		List<MerchantDto> merchants = merchantRepository.findAll().stream().map(model -> {
			MerchantDto merchant = MerchantDto.builder().build();
				BeanUtils.copyProperties(model, merchant);
				return merchant;
			}
		).collect(Collectors.toList());
		
		return merchants;
	}
}
