package com.example.coupon.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class BestCouponResponse {

    private String couponCode;
    private String couponDescription;
    private BigDecimal discountAmount;
    private BigDecimal cartValue;
    private BigDecimal finalCartValue;

}
