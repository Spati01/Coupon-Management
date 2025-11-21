package com.example.coupon.dto;

import com.example.coupon.model.Cart;
import com.example.coupon.model.UserContext;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BestCouponRequest {

    @NotNull
    private UserContext user;
    @NotNull
    private Cart cart;
}
