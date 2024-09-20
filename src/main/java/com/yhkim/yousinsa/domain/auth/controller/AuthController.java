package com.yhkim.yousinsa.domain.auth.controller;


import com.yhkim.yousinsa.domain.auth.dto.ReissueTokenResponse;
import com.yhkim.yousinsa.domain.auth.service.AuthService;
import com.yhkim.yousinsa.global.api.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yhkim.yousinsa.global.api.ApiUtils.success;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @GetMapping("/token")
    public ResponseEntity<ApiUtils.SuccessResponse<ReissueTokenResponse>> refresh(@RequestHeader(name = "Refresh-Token") String refreshToken) {
        return success(HttpStatus.OK, "Access Token 재발급에 성공하였습니다.", authService.refresh(refreshToken));
    }
}
