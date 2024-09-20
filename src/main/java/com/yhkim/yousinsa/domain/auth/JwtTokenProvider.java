package com.yhkim.yousinsa.domain.auth;


import com.yhkim.yousinsa.domain.auth.dto.JwtTokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    
    private final UserDetailsService userDetailsService;
    private final long accessTokenValidTime = 60 * 60 * 1000L; // 1 hour
    private final long refreshTokenValidTime = 30 * 24 * 60 * 60 * 1000L; // 30 days
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String TOKEN_TYPE_CLAIM = "token_type";
    
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private Key key;
    
    /**
     * 시크릿 키 초기화
     */
    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    
    /**
     * 토큰 생성
     *
     * @param username
     * @return JwtTokenInfo
     */
    public JwtTokenInfo generateToken(String username, TokenType tokenType) {
        
        // 클레임 설정
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(TOKEN_TYPE_CLAIM, tokenType);
        
        log.info("createToken - username : {} , type : {} ", username, tokenType);
        
        // 만료 시간 설정
        Date now = new Date();
        long tokenValidTime = (tokenType == TokenType.ACCESS_TOKEN) ? accessTokenValidTime : refreshTokenValidTime;
        Date expirationDate = new Date(now.getTime() + tokenValidTime);
        
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
        
        long expiresIn = (expirationDate.getTime() - now.getTime()) / 1000; // 초 단위로 변환
        
        return JwtTokenInfo.builder()
                .token(token)
                .expiresIn(expiresIn)
                .build();
    }
    
    /**
     * HTTP 요청 헤더에서 JWT 토큰을 추출
     *
     * @param request
     * @return
     */
    public String resolveToken(HttpServletRequest request) {
        
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return resolveTokenFromBearerToken(bearerToken);
    }
    
    /**
     * bearerToken 토큰에서 token만 추출
     *
     * @param bearerToken
     * @return
     */
    public String resolveTokenFromBearerToken(String bearerToken) {
        log.info("token : {}", bearerToken);
        
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
    
    /**
     * JWT 토큰을 복호화하여 인증 정보를 가져옴
     *
     * @param accessToken
     * @return
     */
    public Authentication getAuthentication(String accessToken) {
        String username = parseUsername(accessToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }
    
    /**
     * token에서 username 추출
     *
     * @param token
     * @return
     */
    public String parseUsername(String token) {
        return parseClaims(token).getSubject();
    }
    
    public String parseTokenType(String token) {
        return (String) parseClaims(token).get(TOKEN_TYPE_CLAIM);
    }
    
    /**
     * token에서 Claims 추출
     *
     * @param token
     * @return
     */
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
