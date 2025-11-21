package com.example.coupon.controller;


import com.example.coupon.dto.BestCouponRequest;
import com.example.coupon.dto.BestCouponResponse;
import com.example.coupon.dto.CreateCouponRequest;
import com.example.coupon.dto.CreateCouponResponse;
import com.example.coupon.model.Coupon;
import com.example.coupon.service.CouponService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }


    @PostMapping("/create")
    public ResponseEntity<CreateCouponResponse> createCoupon(
            @Valid @RequestBody CreateCouponRequest request
    ) {
        Coupon savedCoupon = couponService.createCoupon(request);

        CreateCouponResponse response = new CreateCouponResponse(
                "Coupon created successfully!",
                savedCoupon.getCode()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @GetMapping("/all-coupons")
    public ResponseEntity<List<Coupon>> listCoupons() {
        return ResponseEntity.ok(couponService.getAllCoupons());
    }


    @PostMapping("/find-best")
    public ResponseEntity<?> bestCoupon(@Valid @RequestBody BestCouponRequest request) {

        BestCouponResponse resp = couponService.findBestCoupon(request.getUser(), request.getCart());

        if (resp == null) {
            System.out.println("No coupon available at this moment");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No coupons available for this user/cart"));
        }

        System.out.println("This is the best coupon for you : " + resp.getCouponCode());
        return ResponseEntity.ok(resp);
    }

}
