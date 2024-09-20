package com.yhkim.yousinsa.domain.user.service;

import com.yhkim.yousinsa.domain.auth.JwtTokenProvider;
import com.yhkim.yousinsa.domain.auth.TokenType;
import com.yhkim.yousinsa.domain.auth.dto.JwtTokenInfo;
import com.yhkim.yousinsa.domain.auth.entity.RefreshToken;
import com.yhkim.yousinsa.domain.auth.repository.RefreshTokenRepository;
import com.yhkim.yousinsa.domain.user.dto.LoginUserRequest;
import com.yhkim.yousinsa.domain.user.dto.LoginUserResponse;
import com.yhkim.yousinsa.domain.user.dto.SignupUserRequest;
import com.yhkim.yousinsa.domain.user.dto.SignupUserResponse;
import com.yhkim.yousinsa.domain.user.entity.User;
import com.yhkim.yousinsa.domain.user.repository.UserRepository;
import com.yhkim.yousinsa.global.exception.CustomException;
import com.yhkim.yousinsa.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    
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
     * 로그인 처리
     *
     * @param loginUserRequest 로그인 요청 정보
     * @return LoginUserResponse 로그인된 사용자 정보 응답
     * @throws CustomException USERNAME_NOT_FOUND 등록되지 않은 계정
     * @throws CustomException INVALID_PASSWORD 비밀번호 불일치
     */
    @Override
    @Transactional
    public LoginUserResponse login(LoginUserRequest loginUserRequest) {
        
        // username 존재하는지 확인
        User user = userRepository.findByUsername(loginUserRequest.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOT_FOUND));
        
        // 비밀번호 일치하는지 확인
        if (!passwordEncoder.matches(loginUserRequest.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        
        
        // access, refresh 토큰 생성
        JwtTokenInfo accessTokenInfo = jwtTokenProvider.generateToken(user.getUsername(), TokenType.ACCESS_TOKEN);
        JwtTokenInfo refreshTokenInfo = jwtTokenProvider.generateToken(user.getUsername(), TokenType.REFRESH_TOKEN);
        
        // TODO: Redis로 변경
        // refresh 토큰 DB에 upsert
        Optional<RefreshToken> savedRefreshToken = refreshTokenRepository.findByUsername(user.getUsername());
        
        if (savedRefreshToken.isEmpty()) {
            RefreshToken obj = RefreshToken.builder()
                    .username(user.getUsername())
                    .token(refreshTokenInfo.getToken())
                    .build();
            
            refreshTokenRepository.save(obj);
        } else {
            savedRefreshToken.get().setToken(refreshTokenInfo.getToken());
            refreshTokenRepository.save(savedRefreshToken.get());
        }
        
        return LoginUserResponse.builder()
                .accessToken(accessTokenInfo.getToken())
                .expiredAt(accessTokenInfo.getExpiresIn())
                .refreshToken(refreshTokenInfo.getToken())
                .build();
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
