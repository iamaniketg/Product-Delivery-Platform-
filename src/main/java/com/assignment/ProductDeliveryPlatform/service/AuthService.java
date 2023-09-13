package com.assignment.ProductDeliveryPlatform.service;

import com.assignment.ProductDeliveryPlatform.model.AuthenticationToken;
import com.assignment.ProductDeliveryPlatform.repository.AuthTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    AuthTokenRepository authTokenRepository;

    public boolean authenticate(String email, String authTokenValue) {
        AuthenticationToken authToken = authTokenRepository.findFirstByTokenValue(authTokenValue);
        if(authToken == null){
            return false;
        }

        String tokenConnectedEmail = authToken.getCustomer().getEmail();
        return tokenConnectedEmail.equals(email);
    }
}