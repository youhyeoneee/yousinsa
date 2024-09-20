package com.yhkim.yousinsa.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginUserRequest {
    @NotBlank(message = "계정을 입력해주세요.")
    private String username;
    
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
