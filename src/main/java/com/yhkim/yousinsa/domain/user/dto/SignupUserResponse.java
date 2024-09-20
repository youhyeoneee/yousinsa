package com.yhkim.yousinsa.domain.user.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.yhkim.yousinsa.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupUserResponse {
    @JsonProperty("user_id")
    private Integer userId;
    
    public static SignupUserResponse fromEntity(User user) {
        return SignupUserResponse.builder()
                .userId(user.getId())
                .build();
    }
}
