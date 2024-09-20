package com.yhkim.yousinsa.domain.auth.entity;


import com.yhkim.yousinsa.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken extends BaseEntity {
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String token;
    
    public void setToken(String token) {
        this.token = token;
    }
}
