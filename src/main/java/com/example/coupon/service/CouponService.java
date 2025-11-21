package com.example.coupon.service;

import com.example.coupon.dto.BestCouponResponse;
import com.example.coupon.dto.CreateCouponRequest;
import com.example.coupon.model.Cart;
import com.example.coupon.model.Coupon;
import com.example.coupon.model.UserContext;

import java.util.List;

public interface CouponService {

     Coupon createCoupon(CreateCouponRequest request);
     List<Coupon> getAllCoupons();
     BestCouponResponse findBestCoupon(UserContext user, Cart cart);
}
