package com.emerchantpay.task.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.emerchantpay.task.dtos.JwtAuthenticationResponse;
import com.emerchantpay.task.dtos.SigninRequest;
import com.emerchantpay.task.repositories.MerchantRepository;
import com.emerchantpay.task.services.interfaces.AuthenticationService;
import com.emerchantpay.task.services.interfaces.JwtService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private MerchantRepository merchantRepository;
    
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = merchantRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
