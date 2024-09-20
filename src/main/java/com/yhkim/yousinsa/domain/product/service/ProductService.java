package com.yhkim.yousinsa.domain.product.service;

import com.yhkim.yousinsa.domain.product.dto.GetProductsResponse;
import com.yhkim.yousinsa.domain.product.dto.ProductDetailResponse;
import com.yhkim.yousinsa.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    
    
    Page<GetProductsResponse> getProducts(Pageable pageable);
    
    Page<ProductDetailResponse> getProductsWithDetail(Pageable pageable, User user);
    
}
