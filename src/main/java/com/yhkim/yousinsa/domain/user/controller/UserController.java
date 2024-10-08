package com.yhkim.yousinsa.domain.user.controller;

import com.yhkim.yousinsa.domain.user.dto.LoginUserRequest;
import com.yhkim.yousinsa.domain.user.dto.LoginUserResponse;
import com.yhkim.yousinsa.domain.user.dto.SignupUserRequest;
import com.yhkim.yousinsa.domain.user.dto.SignupUserResponse;
import com.yhkim.yousinsa.domain.user.service.UserService;
import com.yhkim.yousinsa.global.api.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yhkim.yousinsa.global.api.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    
    @PostMapping(value = "/signup")
    public ResponseEntity<ApiUtils.SuccessResponse<SignupUserResponse>> signup(@Valid @RequestBody SignupUserRequest signupUserRequest) {
        return success(HttpStatus.CREATED, "회원가입에 성공하였습니다.", userService.signup(signupUserRequest));
    }
    
    @PostMapping(value = "/login")
    public ResponseEntity<ApiUtils.SuccessResponse<LoginUserResponse>> login(@Valid @RequestBody LoginUserRequest loginUserRequest) {
        return success(HttpStatus.OK, "로그인에 성공하였습니다", userService.login(loginUserRequest));
    }
    
}
