package com.yhkim.yousinsa.domain.user.entity;

import com.yhkim.yousinsa.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User extends BaseEntity {
    
    @Comment("계정")
    @Column(unique = true, nullable = false, length = 20)
    private String username;
    
    @Comment("비밀번호")
    @Column(nullable = false)
    private String password;
    
    @Comment("등급")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade = Grade.NONE;
    
}
