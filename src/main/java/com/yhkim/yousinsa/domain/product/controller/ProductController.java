package com.yhkim.yousinsa.domain.product.controller;


import com.yhkim.yousinsa.domain.product.service.ProductService;
import com.yhkim.yousinsa.domain.user.dto.Links;
import com.yhkim.yousinsa.domain.user.entity.User;
import com.yhkim.yousinsa.domain.user.service.UserService;
import com.yhkim.yousinsa.global.api.ApiUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yhkim.yousinsa.global.api.ApiUtils.successWithLinks;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Slf4j
public class ProductController {
    
    private final ProductService productService;
    private final UserService userService;
    
    @GetMapping
    public <T> ResponseEntity<ApiUtils.SuccessLinksResponse<List<T>, Links>> getProducts(
            Authentication authentication,
            Pageable pageable,
            @RequestParam(required = false, name = "brand") String brandName,
            HttpServletRequest request) {
        
        Page<T> productsPage;
        if (authentication == null) {
            log.info("로그인 x");
            productsPage = (Page<T>) productService.getProductInfos(pageable, brandName);
        } else {
            log.info(authentication.getName());
            User user = findUserByAuthentication(authentication);
            productsPage = (Page<T>) productService.getProductDetails(pageable, user, brandName);
        }
        
        Links links = createLinks(productsPage, request);
        return successWithLinks(HttpStatus.OK, "상품 목록 조회에 성공하였습니다.", productsPage.getContent(), links);
    }
    
    private User findUserByAuthentication(Authentication authentication) {
        String username = authentication.getName();
        return userService.findByUsername(username);
    }
    
    private <T> Links createLinks(Page<T> page, HttpServletRequest request) {
        String baseUrl = request.getRequestURL().toString() + "?" + request.getQueryString();
        
        return Links.builder()
                .self(baseUrl)
                .first(replacePageParam(baseUrl, 0))
                .last(replacePageParam(baseUrl, page.getTotalPages() - 1))
                .prev(page.hasPrevious() ? replacePageParam(baseUrl, page.getNumber() - 1) : null)
                .next(page.hasNext() ? replacePageParam(baseUrl, page.getNumber() + 1) : null)
                .build();
    }
    
    private String replacePageParam(String url, int page) {
        return url.replaceFirst("(page=)\\d+", "$1" + page);
    }
}
