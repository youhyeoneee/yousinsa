package com.yhkim.yousinsa.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "timestamp")
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "timestamp")
    private LocalDateTime updatedAt;
    
    @Column(name = "deleted_at", columnDefinition = "timestamp")
    private LocalDateTime deletedAt;
    
    public void setDeletedAt() {
        deletedAt = LocalDateTime.now();
    }
}


