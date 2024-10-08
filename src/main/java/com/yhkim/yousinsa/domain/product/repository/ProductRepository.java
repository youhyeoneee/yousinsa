package com.yhkim.yousinsa.domain.product.repository;

import com.yhkim.yousinsa.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    Page<Product> findAllByOrderByIdDesc(Pageable pageable);
    
    Page<Product> findAllByBrandIdInOrderByIdDesc(List<Integer> brandIds, Pageable pageable);
    
}
