package com.yhkim.yousinsa.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ReissueTokenResponse {
    
    @JsonProperty("access_token")
    String accessToken;
    @JsonProperty("expired_at")
    private long expiredAt;
}
