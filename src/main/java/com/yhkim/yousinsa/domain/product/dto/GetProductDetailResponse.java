package com.yhkim.yousinsa.domain.product.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetProductDetailResponse {
    private Integer defaultPrice;
    private Integer discountedPrice;
    private Double discountRate;
    private String productInfo;
    private String brandInfo;
}
