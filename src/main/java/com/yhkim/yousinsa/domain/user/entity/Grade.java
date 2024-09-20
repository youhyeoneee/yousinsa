package com.yhkim.yousinsa.domain.user.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grade {
    
    NONE(0, "등급없음"),
    NEWBE(1, "뉴비"),
    ROOKIE(2, "루키"),
    MEMBER(3, "멤버"),
    BRONZE(4, "브론즈"),
    SILVER(5, "실버"),
    GOLD(6, "골드"),
    PLATINUM(7, "플래티넘"),
    DIAMOND(8, "다이아몬드");
    
    private final Integer level;
    private final String name;
}
