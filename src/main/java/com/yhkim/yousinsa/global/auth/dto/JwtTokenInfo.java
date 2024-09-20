package com.yhkim.yousinsa.global.auth.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtTokenInfo {
    private final String token;
    private final long expiresIn;
}
