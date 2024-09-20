package com.yhkim.yousinsa.domain.product.service;

import com.yhkim.yousinsa.domain.product.dto.GetProductsResponse;
import com.yhkim.yousinsa.domain.product.dto.ProductDetailResponse;
import com.yhkim.yousinsa.domain.product.repository.ProductRepository;
import com.yhkim.yousinsa.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    
    @Override
    public Page<GetProductsResponse> getProducts(Pageable pageable) {
        return productRepository.findAllByOrderByIdDesc(pageable)
                .map(GetProductsResponse::fromEntity);
    }
    
    @Override
    public Page<ProductDetailResponse> getProductsWithDetail(Pageable pageable, User user) {
        return null;
    }
}
