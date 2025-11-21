package com.example.coupon.dto;

import com.example.coupon.model.Eligibility;
import com.example.coupon.model.enums.DiscountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Setter
@Getter
public class CreateCouponRequest {
    @NotBlank
    private String code;
    private String description;
    @NotNull
    private DiscountType discountType;
    private BigDecimal discountValue;
    private BigDecimal maxDiscountAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer usageLimitPerUser;
    private Eligibility eligibility;
}
