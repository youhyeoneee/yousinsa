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
public class GetProductsResponse {
    private ProductInfo productInfo;
    private BrandInfo brandInfo;
    
    public static GetProductsResponse fromEntity(Product product) {
        
        return GetProductsResponse.builder()
                .productInfo(product.getProductInfo())
                .brandInfo(product.getBrand().getBrandInfo())
                .build();
    }
}
