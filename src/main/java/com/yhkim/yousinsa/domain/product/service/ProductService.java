package com.yhkim.yousinsa.domain.product.service;

import com.yhkim.yousinsa.domain.product.dto.GetProductDetailResponse;
import com.yhkim.yousinsa.domain.product.dto.GetProductResponse;
import com.yhkim.yousinsa.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    
    
    Page<GetProductResponse> getProductInfos(Pageable pageable, String brandName);
    
    Page<GetProductDetailResponse> getProductDetails(Pageable pageable, User user, String brandName);
    
}
