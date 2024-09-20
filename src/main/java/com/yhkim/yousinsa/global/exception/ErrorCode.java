package com.yhkim.yousinsa.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // user
    INVALID_INPUT_FORMAT(HttpStatus.BAD_REQUEST, "E400000", "잘못된 입력 형식입니다."),
    INVALID_USERNAME_FORMAT(HttpStatus.BAD_REQUEST, "E400001", "잘못된 사용자 이름 형식입니다."),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "E400002", "잘못된 비밀번호 형식입니다."),
    MISSING_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "E400006", "요청 헤더에 'Refresh-Token'이 누락되었습니다."),
    INVALID_TOKEN_TYPE(HttpStatus.BAD_REQUEST, "E400007", "잘못된 토큰 유형입니다. 이 작업에는 리프레시 토큰만 허용됩니다."),
    INVALID_JSON(HttpStatus.BAD_REQUEST, "E400008", "JSON 형식이 잘못되었습니다."),
    
    // user
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "E401001", "리프레시 토큰이 유효하지 않거나 존재하지 않습니다."),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "E401002", "엑세스 토큰이 만료되었습니다. 새로운 엑세스 토큰을 발급받으려면 리프레시 토큰을 사용하세요."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "E401003", "리프레시 토큰이 만료되었습니다. 계속하려면 다시 로그인하세요."),
    REFRESH_TOKEN_MISMATCH(HttpStatus.UNAUTHORIZED, "E401004", "제공된 리프레시 토큰이 저장된 토큰과 일치하지 않습니다."),
    
    // user
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "E404001", "등록되지 않은 계정입니다."),
    
    
    // user
    USERNAME_ALREADY_EXIST(HttpStatus.CONFLICT, "E409001", "이미 존재하는 계정입니다.");
    
    private final HttpStatus httpStatus;
    private final String errorCode;
    private String message;
    
    public void setMessage(String message) {
        this.message = message;
    }
}
