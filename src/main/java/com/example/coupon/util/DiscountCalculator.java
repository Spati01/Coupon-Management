package com.example.coupon.util;

import com.example.coupon.model.Coupon;
import com.example.coupon.model.enums.DiscountType;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DiscountCalculator {

    public static BigDecimal computeDiscount(Coupon coupon, BigDecimal cartValue){
        if(coupon == null || coupon.getDiscountType() == null)return BigDecimal.ZERO;
        if(coupon.getDiscountValue() == null)return BigDecimal.ZERO;

        if(coupon.getDiscountType() == DiscountType.FLAT){
            return coupon.getDiscountValue().setScale(2, RoundingMode.HALF_UP);

        }else{
            BigDecimal precent = coupon.getDiscountValue();
            BigDecimal raw = cartValue.multiply(precent).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);

            if(coupon.getMaxDiscountAmount() != null){
                raw = raw.min(coupon.getMaxDiscountAmount());
            }
            return raw.setScale(2, RoundingMode.HALF_UP);
        }
    }
}
