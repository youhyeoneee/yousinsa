package com.yhkim.yousinsa.domain.brand.repository;

import com.yhkim.yousinsa.domain.brand.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    
    @Query("SELECT b FROM Brand b WHERE " +
            "LOWER(b.koreanName) LIKE LOWER(CONCAT('%', :searchKeyword, '%')) OR " +
            "LOWER(b.englishName) LIKE LOWER(CONCAT('%', :searchKeyword, '%'))")
    List<Brand> findByKoreanNameOrEnglishNameContaining(@Param("searchKeyword") String searchKeyword);
    
}

