package com.yhkim.yousinsa.global.api;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhkim.yousinsa.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Slf4j
public class ApiUtils {
    
    public static <T> ResponseEntity<SuccessResponse<T>> success(HttpStatus httpStatus, String message, T data) {
        SuccessResponse<T> successResponse = new SuccessResponse<>(httpStatus.value(), message, data);
        return ResponseEntity.status(httpStatus).body(successResponse);
    }
    
    public static <T, M> ResponseEntity<SuccessLinksResponse<T, M>> successWithLinks(HttpStatus httpStatus, String message, T data, M links) {
        SuccessLinksResponse<T, M> successResponse = new SuccessLinksResponse<>(httpStatus.value(), message, data, links);
        return ResponseEntity.status(httpStatus).body(successResponse);
    }
    
    public static ResponseEntity<FailedResponse> failed(ErrorCode errorCode) {
        FailedResponse failedResponse = new FailedResponse(errorCode);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(failedResponse);
    }
    
    @Getter
    @AllArgsConstructor
    public static class BasicResponse {
        private final boolean success;
        @JsonProperty("http_status")
        private final int httpStatus;
        private final String message;
    }
    
    @Getter
    public static class SuccessResponse<T> extends BasicResponse {
        private final T data;
        
        public SuccessResponse(int httpStatus, String message, T data) {
            super(true, httpStatus, message);
            this.data = data;
        }
    }
    
    @Getter
    public static class SuccessLinksResponse<T, M> extends BasicResponse {
        private final T data;
        private final M links;
        
        public SuccessLinksResponse(int httpStatus, String message, T data, M links) {
            super(true, httpStatus, message);
            this.data = data;
            this.links = links;
        }
    }
    
    @Getter
    public static class FailedResponse extends BasicResponse {
        @JsonProperty("error_code")
        private final String errorCode;
        
        public FailedResponse(ErrorCode errorCode) {
            super(false, errorCode.getHttpStatus().value(), errorCode.getMessage());
            this.errorCode = errorCode.getErrorCode();
        }
    }
    
    public static void setResponse(HttpServletResponse response, ErrorCode errorCode) {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(Map.of(
                    "success", false,
                    "message", errorCode.getMessage(),
                    "http_status", errorCode.getHttpStatus().value(),
                    "error_code", errorCode.getErrorCode()
            ));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error("Error writing JSON response", e);
        }
    }
}
