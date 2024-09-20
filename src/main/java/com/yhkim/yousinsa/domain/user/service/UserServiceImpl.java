package com.yhkim.yousinsa.domain.user.service;

import com.yhkim.yousinsa.domain.user.dto.SignupUserRequest;
import com.yhkim.yousinsa.domain.user.dto.SignupUserResponse;
import com.yhkim.yousinsa.domain.user.entity.User;
import com.yhkim.yousinsa.domain.user.repository.UserRepository;
import com.yhkim.yousinsa.global.exception.CustomException;
import com.yhkim.yousinsa.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * 회원 가입 처리
     *
     * @param signupUserRequest 회원 가입 요청 정보
     * @return SignupUserResponse 가입된 사용자 정보 응답
     * @throws CustomException 계정이 이미 존재하는 경우 발생
     */
    @Override
    public SignupUserResponse signup(SignupUserRequest signupUserRequest) {
        if (isUserExist(signupUserRequest.getUsername())) {
            throw new CustomException(ErrorCode.USERNAME_ALREADY_EXIST);
        }
        
        String encodedPassword = passwordEncoder.encode(signupUserRequest.getPassword());
        signupUserRequest.setPassword(encodedPassword);
        
        User savedUser = userRepository.save(signupUserRequest.toEntity());
        return SignupUserResponse.fromEntity(savedUser);
    }
    
    /**
     * 계정 존재 여부 확인
     *
     * @param username 확인할 계정
     * @return boolean 존재 여부 (true: 존재함, false: 존재 하지 않음)
     */
    private boolean isUserExist(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
