package com.yhkim.yousinsa.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginUserResponse {
    
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expired_at")
    private long expiredAt;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
