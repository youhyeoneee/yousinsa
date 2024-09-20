package com.yhkim.yousinsa.domain.product.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yhkim.yousinsa.domain.brand.dto.BrandInfo;
import com.yhkim.yousinsa.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetProductDetailResponse {
    private Integer defaultPrice;
    private Integer discountedPrice;
    private Integer discountRate;
    private ProductInfo productInfo;
    private BrandInfo brandInfo;
    
    public static GetProductDetailResponse fromEntity(Product product, Integer discountRate) {
        
        return GetProductDetailResponse.builder()
                .defaultPrice(product.getPrice())
                .discountedPrice(product.getDiscountedPrice(discountRate))
                .discountRate(discountRate)
                .productInfo(product.getProductInfo())
                .brandInfo(product.getBrand().getBrandInfo())
                .build();
    }
}
