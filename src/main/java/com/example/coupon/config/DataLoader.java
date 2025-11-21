package com.example.coupon.config;



import com.example.coupon.model.*;
import com.example.coupon.model.enums.DiscountType;
import com.example.coupon.model.enums.Role;
import com.example.coupon.repository.CouponRepository;
import com.example.coupon.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {

    private final CouponRepository repo;
    private final UserRepository userRepository;

    public DataLoader(CouponRepository repo, UserRepository userRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
    }

    @Override
        public void run(String... args) {
            loadTestCoupons();
            loadDemoUser();
        System.out.println("Test Coupons and Demo User Loaded Successfully!");
        }

        private void loadTestCoupons() {


// GOLD Users Only Coupon
            Coupon c1 = new Coupon();
            c1.setCode("FLAT100");
            c1.setDescription("Flat Rs 100 off for Gold users");
            c1.setDiscountType(DiscountType.FLAT);
            c1.setDiscountValue(BigDecimal.valueOf(100));
            c1.setStartDate(LocalDateTime.now().minusDays(1));
            c1.setEndDate(LocalDateTime.now().plusMonths(6));

            Eligibility e1 = new Eligibility();
            e1.setAllowedUserTiers(Collections.singletonList("GOLD"));
            c1.setEligibility(e1);
            c1.setUsageByUser(new HashMap<>());
            repo.save(c1);


// General Big Sale

            Coupon c2 = new Coupon();
            c2.setCode("MEGA2025");
            c2.setDescription("Mega Year Sale - Flat ₹150 off everyone!");
            c2.setDiscountType(DiscountType.FLAT);
            c2.setDiscountValue(BigDecimal.valueOf(150));
            c2.setStartDate(LocalDateTime.now().minusDays(1));
            c2.setEndDate(LocalDateTime.now().plusMonths(12));

            Eligibility e2 = new Eligibility();
            e2.setMinCartValue(BigDecimal.valueOf(500)); // 👈 IMPORTANT RULE

            c2.setEligibility(e2);
            c2.setUsageByUser(new HashMap<>());
            repo.save(c2);


// First Order Only Coupon
            Coupon c3 = new Coupon();
            c3.setCode("FIRST200");
            c3.setDescription("Get ₹200 off on your first order");
            c3.setDiscountType(DiscountType.FLAT);
            c3.setDiscountValue(BigDecimal.valueOf(200));
            c3.setStartDate(LocalDateTime.now().minusDays(1));
            c3.setEndDate(LocalDateTime.now().plusMonths(12));

            Eligibility e3 = new Eligibility();
            e3.setFirstOrderOnly(true);
            c3.setEligibility(e3);
            c3.setUsageByUser(new HashMap<>());
            repo.save(c3);

            System.out.println("4 Coupons successfully inserted in memory database!");


    }




    // Seed Demo User

    private void loadDemoUser() {
        String email = "hire-me@anshumat.org";
        if (!userRepository.existsByEmail(email)) {
            User demoUser = new User();
            demoUser.setEmail(email);
            demoUser.setPassword("HireMe@2025!");
            demoUser.setRole(Role.ADMIN);
            userRepository.save(demoUser);
            System.out.println("Demo user created successfully!");
        } else {
            System.out.println("Demo user already exists!");
        }
    }
}

