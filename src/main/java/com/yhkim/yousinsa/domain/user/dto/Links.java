package com.yhkim.yousinsa.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Links {
    private String self;
    private String first;
    private String last;
    private String prev;
    private String next;
}
