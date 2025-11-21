package com.example.coupon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserContext {

    private String userId;
    private String userTier;
    private String country;
    private BigDecimal lifetimeSpend;
    private Integer ordersPlaced;
}
