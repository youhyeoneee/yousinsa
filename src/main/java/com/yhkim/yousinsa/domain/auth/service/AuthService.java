package com.yhkim.yousinsa.domain.auth.service;


import com.yhkim.yousinsa.domain.auth.dto.ReissueTokenResponse;

public interface AuthService {
    
    ReissueTokenResponse refresh(String refreshToken);
}
