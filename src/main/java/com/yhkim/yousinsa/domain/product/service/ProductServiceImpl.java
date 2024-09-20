package com.yhkim.yousinsa.domain.product.service;

import com.yhkim.yousinsa.domain.product.dto.GetProductDetailResponse;
import com.yhkim.yousinsa.domain.product.dto.GetProductResponse;
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
    public Page<GetProductResponse> getProductInfos(Pageable pageable) {
        return productRepository.findAllByOrderByIdDesc(pageable)
                .map(GetProductResponse::fromEntity);
    }
    
    @Override
    public Page<GetProductDetailResponse> getProductDetails(Pageable pageable, User user) {
        return null;
    }
}
