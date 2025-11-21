package com.example.coupon.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
public class CartItem {

    private String category;


    @NotBlank(message = "productId cannot be blank")
    private String productId;

    @JsonAlias({"price", "unitPrice"})
    private BigDecimal unitPrice;

    @NotNull(message = "quantity cannot be null")
    private Integer quantity;


}