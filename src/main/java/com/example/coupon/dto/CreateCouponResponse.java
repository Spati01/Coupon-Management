package com.example.coupon.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCouponResponse {
    private String message;
    private String couponCode;
}
