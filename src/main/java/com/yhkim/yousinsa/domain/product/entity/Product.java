package com.yhkim.yousinsa.domain.product.entity;


import com.yhkim.yousinsa.domain.brand.entity.Brand;
import com.yhkim.yousinsa.domain.product.dto.ProductInfo;
import com.yhkim.yousinsa.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "products")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Product extends BaseEntity {
    @Comment("상품명")
    @Column(unique = true, nullable = false)
    private String name;
    
    @Comment("브랜드명")
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;
    
    @Comment("가격")
    @Column(nullable = false)
    private Integer price;
    
    public ProductInfo getProductInfo() {
        return ProductInfo
                .builder()
                .id(getId())
                .name(name)
                .build();
    }
    
    public Integer getDiscountedPrice(Integer discountRate) {
        if (discountRate < 0 || discountRate > 100) {
            throw new IllegalArgumentException("할인율은 0에서 100 사이의 값이어야 합니다.");
        }
        
        Integer discountAmount = (price * discountRate) / 100;
        return price - discountAmount;
    }
}
