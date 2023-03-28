package com.emerchantpay.task.services.implementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emerchantpay.task.dtos.MerchantDto;
import com.emerchantpay.task.models.Merchant;
import com.emerchantpay.task.repositories.MerchantRepository;
import com.emerchantpay.task.services.interfaces.MerchantService;
import com.emerchantpay.task.validations.interfaces.MerchantValidation;

@Service
public class MerchantServiceImpl implements MerchantService {

	@Autowired
	private MerchantRepository merchantRepository;
	
	@Autowired
	private MerchantValidation merchantValidation;
	
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

	@Override
	public void delete(Long id) {
		merchantValidation.exists(id);
		merchantValidation.hasTransaction(id);
		merchantRepository.deleteById(id);
	}

	@Override
	public MerchantDto update(MerchantDto merchantDto) {
		Optional<Merchant> merchantOptional = merchantRepository.findById(merchantDto.getId());
		if(merchantOptional.isPresent()) {
			Merchant merchant = merchantOptional.get();
			BeanUtils.copyProperties(merchantDto, merchant);
			merchantRepository.save(merchant);
		}
		return merchantDto;
	}
}
