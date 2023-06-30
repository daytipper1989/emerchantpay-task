package com.emerchantpay.task.services.implementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emerchantpay.task.dtos.MerchantDto;
import com.emerchantpay.task.models.Merchant;
import com.emerchantpay.task.repositories.MerchantRepository;
import com.emerchantpay.task.services.interfaces.JwtService;
import com.emerchantpay.task.services.interfaces.MerchantService;
import com.emerchantpay.task.validations.interfaces.MerchantValidation;

@Service
public class MerchantServiceImpl implements MerchantService {

	@Autowired
	private MerchantRepository merchantRepository;

	@Autowired
	private MerchantValidation merchantValidation;

	@Autowired
	private JwtService jwtService;

	@Override
	public List<MerchantDto> getAll() {

		merchantValidation.isAdmin(jwtService.getLoggedInUser());

		return merchantRepository.findAll().stream().map(model -> {
			MerchantDto merchant = MerchantDto.builder().build();
			BeanUtils.copyProperties(model, merchant);
			return merchant;
		}).collect(Collectors.toList());
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
		if (merchantOptional.isPresent()) {
			Merchant merchant = merchantOptional.get();
			BeanUtils.copyProperties(merchantDto, merchant);
			merchantRepository.save(merchant);
		}
		return merchantDto;
	}

	@Override
	@Transactional
	public MerchantDto addAmount(Long id, double amount) {

		MerchantDto merchantDto = null;

		Optional<Merchant> merchantOptional = merchantRepository.findById(id);
		if (merchantOptional.isPresent()) {
			Merchant merchant = merchantOptional.get();
			merchant.setTotalTransactionSum(merchant.getTotalTransactionSum() + amount);

			merchantDto = new MerchantDto();
			BeanUtils.copyProperties(merchant, merchantDto);

		}
		return merchantDto;
	}

	@Override
	@Transactional
	public MerchantDto subtractAmount(Long id, double amount) {

		MerchantDto merchantDto = null;

		Optional<Merchant> merchantOptional = merchantRepository.findById(id);
		if (merchantOptional.isPresent()) {
			Merchant merchant = merchantOptional.get();
			merchant.setTotalTransactionSum(merchant.getTotalTransactionSum() - amount);

			merchantDto = new MerchantDto();
			BeanUtils.copyProperties(merchant, merchantDto);

		}
		return merchantDto;
	}

	@Override
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) {
				return merchantRepository.findByEmail(username)
						.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			}
		};
	}
}
