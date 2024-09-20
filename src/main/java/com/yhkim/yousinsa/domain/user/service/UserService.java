package com.yhkim.yousinsa.domain.user.service;


import com.yhkim.yousinsa.domain.user.dto.LoginUserRequest;
import com.yhkim.yousinsa.domain.user.dto.LoginUserResponse;
import com.yhkim.yousinsa.domain.user.dto.SignupUserRequest;
import com.yhkim.yousinsa.domain.user.dto.SignupUserResponse;
import com.yhkim.yousinsa.domain.user.entity.User;

public interface UserService {
    SignupUserResponse signup(SignupUserRequest signupUserRequest);
    
    LoginUserResponse login(LoginUserRequest loginUserRequest);
    
    public User findByUsername(String username);
}
