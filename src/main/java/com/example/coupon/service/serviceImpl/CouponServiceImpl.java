package com.example.coupon.service.serviceImpl;

import com.example.coupon.dto.BestCouponResponse;
import com.example.coupon.dto.CreateCouponRequest;
import com.example.coupon.exception.ApiException;
import com.example.coupon.exception.DuplicateCouponException;
import com.example.coupon.model.Cart;
import com.example.coupon.model.Coupon;
import com.example.coupon.model.Eligibility;
import com.example.coupon.model.UserContext;
import com.example.coupon.repository.CouponRepository;
import com.example.coupon.service.CouponService;
import com.example.coupon.util.DateUtils;
import com.example.coupon.util.DiscountCalculator;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class CouponServiceImpl implements CouponService {

    private CouponRepository couponRepository;

    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public Coupon createCoupon(CreateCouponRequest request) {
        String code = request.getCode();


        if (code == null || code.isBlank()) {
            throw new ApiException("Coupon code cannot be null or blank");
        }

        if(couponRepository.findByCode(code).isPresent()){
            System.out.println("Coupon is already present : " + code);
            throw new DuplicateCouponException("Coupon code already exists: "+ code);
        }

        Coupon coupon = getCoupon(request);

        couponRepository.save(coupon);
        return coupon;

    }

    private static Coupon getCoupon(CreateCouponRequest request) {
        Coupon coupon = new Coupon();
        coupon.setCode(request.getCode());
        coupon.setDescription(request.getDescription());
        coupon.setDiscountType(request.getDiscountType());
        coupon.setDiscountValue(request.getDiscountValue() == null ? BigDecimal.ZERO : request.getDiscountValue());
        coupon.setMaxDiscountAmount(request.getMaxDiscountAmount());
        coupon.setStartDate(request.getStartDate());
        coupon.setEndDate(request.getEndDate());
        coupon.setUsageLimitPerUser(request.getUsageLimitPerUser());
        coupon.setEligibility(request.getEligibility());
        coupon.setUsageByUser(new HashMap<>());
        return coupon;
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public BestCouponResponse findBestCoupon(UserContext user, Cart cart) {
        LocalDateTime now = DateUtils.now();
        BigDecimal cartValue = cart.calculateCartValue();

        List<Coupon> candidates = couponRepository.findAll().stream()
                .filter(c -> isWithinDates(c, now))
                .filter(c -> isWithinUsageLimit(c, user.getUserId()))
                .filter(c -> evaluateEligibility(c, user, cart, cartValue))
                .toList();

        if(candidates.isEmpty())return null;

        List<Candidate> list = new ArrayList<>();
        for(Coupon c : candidates){
            BigDecimal discount = DiscountCalculator.computeDiscount(c,cartValue);
            discount = discount.min(cartValue);
            list.add(new Candidate(c, discount));
        }

        list.sort(Comparator
                .comparing(Candidate::getDiscount).reversed()
                .thenComparing(e -> e.coupon.getEndDate(), Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(e -> e.coupon.getCode()));

        Candidate best = list.get(0);
        BigDecimal finalCartValue = cartValue.subtract(best.discount).max(BigDecimal.ZERO);

        BestCouponResponse resp = new BestCouponResponse();
        resp.setCouponCode(best.coupon.getCode());
        resp.setCouponDescription(best.coupon.getDescription());
        resp.setDiscountAmount(best.discount);
        resp.setFinalCartValue(finalCartValue);
        resp.setCartValue(cartValue);

        return resp;


    }

    private boolean evaluateEligibility(Coupon c, UserContext user, Cart cart, BigDecimal cartValue) {

        Eligibility e = c.getEligibility();
        if(e == null) return true;

        // UserBase
        if (e.getAllowedUserTiers() != null && !e.getAllowedUserTiers().isEmpty()) {
            if (user.getUserTier() == null || !e.getAllowedUserTiers().contains(user.getUserTier())) return false;
        }
        if (e.getMinLifetimeSpend() != null) {
            if (user.getLifetimeSpend() == null || user.getLifetimeSpend().compareTo(e.getMinLifetimeSpend()) < 0) return false;
        }
        if (e.getMinOrdersPlaced() != null) {
            if (user.getOrdersPlaced() == null || user.getOrdersPlaced() < e.getMinOrdersPlaced()) return false;
        }
        if (Boolean.TRUE.equals(e.getFirstOrderOnly())) {
            if (user.getOrdersPlaced() != null && user.getOrdersPlaced() > 0) return false;
        }
        if (e.getAllowedCountries() != null && !e.getAllowedCountries().isEmpty()) {
            if (user.getCountry() == null || !e.getAllowedCountries().contains(user.getCountry())) return false;
        }
   // CartBase

        if (e.getMinCartValue() != null && cartValue.compareTo(e.getMinCartValue()) < 0) return false;
        if (e.getMinItemsCount() != null && cart.totalItemsCount() < e.getMinItemsCount()) return false;

        Set<String> cartCategories = cart.itemCategories();
        if (e.getApplicableCategories() != null && !e.getApplicableCategories().isEmpty()) {
            boolean any = false;
            for (String ccat : e.getApplicableCategories()) {
                if (cartCategories.contains(ccat)) { any = true; break; }
            }
            if (!any) return false;
        }
        if (e.getExcludedCategories() != null && !e.getExcludedCategories().isEmpty()) {
            for (String excl : e.getExcludedCategories()) {
                if (cartCategories.contains(excl)) return false;
            }
        }
        return true;

    }

    private boolean isWithinUsageLimit(Coupon c, String userId) {
        Integer limit = c.getUsageLimitPerUser();
        if(limit == null)return true;
        Integer used = c.getUsageByUser() != null ? c.getUsageByUser().getOrDefault(userId, 0) : 0;
        return used < limit;
    }

    private boolean isWithinDates(Coupon c, LocalDateTime now) {
        LocalDateTime start = c.getStartDate();
        LocalDateTime end = c.getEndDate();
        if(start != null && now.isBefore(start))return false;
        if(end != null && now.isAfter(end))return false;
        return true;
    }


    @Getter
    private static class Candidate{
        private final Coupon coupon;
        private final BigDecimal discount;

        public Candidate(Coupon coupon, BigDecimal discount){
            this.coupon = coupon;
            this.discount = discount;
        }


    }
}
