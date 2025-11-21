package com.example.coupon.exception;

public class DuplicateCouponException  extends ApiException{
    public DuplicateCouponException(String msg) {
        super(msg);
    }
}
