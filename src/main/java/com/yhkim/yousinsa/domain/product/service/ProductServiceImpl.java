package com.yhkim.yousinsa.domain.product.service;

import com.yhkim.yousinsa.domain.brand.entity.Brand;
import com.yhkim.yousinsa.domain.brand.repository.BrandRepository;
import com.yhkim.yousinsa.domain.product.dto.GetProductDetailResponse;
import com.yhkim.yousinsa.domain.product.dto.GetProductResponse;
import com.yhkim.yousinsa.domain.product.repository.ProductRepository;
import com.yhkim.yousinsa.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    
    @Override
    public Page<GetProductResponse> getProductInfos(Pageable pageable, String brandName) {
        
        if (isBrandNameSearchable(brandName)) {
            List<Integer> brandIds = convertBrandNameToBrandIds(brandName);
            return productRepository.findAllByBrandIdInOrderByIdDesc(brandIds, pageable)
                    .map(GetProductResponse::fromEntity);
        } else {
            return productRepository.findAllByOrderByIdDesc(pageable)
                    .map(GetProductResponse::fromEntity);
        }
    }
    
    @Override
    public Page<GetProductDetailResponse> getProductDetails(Pageable pageable, User user, String brandName) {
        // 등급 + 요일에 따른 할인율
        Integer discountRate = user.getGrade().getDiscountRate() + getDiscountRateByDay();
        
        if (isBrandNameSearchable(brandName)) {
            List<Integer> brandIds = convertBrandNameToBrandIds(brandName);
            return productRepository.findAllByBrandIdInOrderByIdDesc(brandIds, pageable)
                    .map(product -> GetProductDetailResponse.fromEntity(product, discountRate));
        } else {
            return productRepository.findAllByOrderByIdDesc(pageable)
                    .map(product -> GetProductDetailResponse.fromEntity(product, discountRate));
        }
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
    
    private boolean isBrandNameSearchable(String brandName) {
        return brandName != null && !brandName.isEmpty();
    }
    
    private List<Integer> convertBrandNameToBrandIds(String brandName) {
        log.info("검색할 브랜드 : {} ", brandName);
        List<Brand> brands = brandRepository.findByKoreanNameOrEnglishNameContaining(brandName);
        for (Brand brand : brands) {
            log.info(brand.getEnglishName() + " / " + brand.getKoreanName());
        }
        
        List<Integer> brandIds = brands.stream().map(Brand::getId).collect(Collectors.toList());
        
        for (Integer brand : brandIds) {
            log.info("id " + brand);
        }
        
        return brandIds;
    }
}
