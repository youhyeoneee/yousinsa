package com.yhkim.yousinsa.domain.user.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grade {
    
    NONE(0, "등급없음", 0),
    NEWBE(1, "뉴비", 2),
    ROOKIE(2, "루키", 4),
    MEMBER(3, "멤버", 5),
    BRONZE(4, "브론즈", 6),
    SILVER(5, "실버", 7),
    GOLD(6, "골드", 8),
    PLATINUM(7, "플래티넘", 9),
    DIAMOND(8, "다이아몬드", 10);
    
    private final Integer level;
    private final String name;
    private final Integer discountRate;
}
