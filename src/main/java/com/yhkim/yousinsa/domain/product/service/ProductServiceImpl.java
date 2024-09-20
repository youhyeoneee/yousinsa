package com.yhkim.yousinsa.domain.product.service;

import com.yhkim.yousinsa.domain.product.dto.GetProductDetailResponse;
import com.yhkim.yousinsa.domain.product.dto.GetProductResponse;
import com.yhkim.yousinsa.domain.product.repository.ProductRepository;
import com.yhkim.yousinsa.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

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
        // 등급 + 요일에 따른 할인율
        Integer discountRate = user.getGrade().getDiscountRate() + getDiscountRateByDay();
        
        return productRepository.findAllByOrderByIdDesc(pageable)
                .map(product -> GetProductDetailResponse.fromEntity(product, discountRate));
    }
    
    private Integer getDiscountRateByDay() {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        int dayNumber = dayOfWeek.getValue();
        
        // 월요일에 1% 할인 추가
        if (dayNumber == 1) {
            return 1;
        } else {
            return 0;
        }
    }
}
