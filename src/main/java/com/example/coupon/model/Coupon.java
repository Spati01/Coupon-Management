package com.example.coupon.model;

import com.example.coupon.model.enums.DiscountType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;



@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Coupon {
    private String code;
    private String description;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private BigDecimal maxDiscountAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer usageLimitPerUser;
    private Eligibility eligibility;
    private Map<String, Integer> usageByUser;


}
