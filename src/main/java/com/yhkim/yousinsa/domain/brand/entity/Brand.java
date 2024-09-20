package com.yhkim.yousinsa.domain.brand.entity;

import com.yhkim.yousinsa.domain.brand.dto.BrandInfo;
import com.yhkim.yousinsa.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "brands")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Brand extends BaseEntity {
    @Comment("한국어 브랜드명")
    @Column(unique = true, nullable = false)
    private String koreanName;
    
    @Comment("영어 브랜드명")
    @Column(unique = true, nullable = false)
    private String englishName;
    
    public BrandInfo getBrandInfo() {
        return BrandInfo
                .builder()
                .id(getId())
                .koreanName(koreanName)
                .englishName(englishName)
                .build();
    }
}
