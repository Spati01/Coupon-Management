package com.example.coupon.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Eligibility {
    private List<String> allowedUserTiers;
    private BigDecimal minLifetimeSpend;
    private Integer minOrdersPlaced;
    private Boolean firstOrderOnly;
    private List<String> allowedCountries;

    private BigDecimal minCartValue;
    private List<String> applicableCategories;
    private List<String> excludedCategories;
    private Integer minItemsCount;


}