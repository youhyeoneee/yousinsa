package com.yhkim.yousinsa.domain.auth.repository;


import com.yhkim.yousinsa.domain.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    
    Optional<RefreshToken> findByUsername(String username);
}
