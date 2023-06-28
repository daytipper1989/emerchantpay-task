package com.emerchantpay.task.services.interfaces;

import com.emerchantpay.task.dtos.JwtAuthenticationResponse;
import com.emerchantpay.task.dtos.SigninRequest;

public interface AuthenticationService {
	JwtAuthenticationResponse signin(SigninRequest request);
}
