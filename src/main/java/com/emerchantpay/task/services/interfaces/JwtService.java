package com.emerchantpay.task.services.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

import com.emerchantpay.task.models.Merchant;

public interface JwtService {
    String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
    
    Merchant getLoggedInUser();
}
