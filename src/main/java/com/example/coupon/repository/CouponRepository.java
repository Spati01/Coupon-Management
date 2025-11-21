package com.example.coupon.repository;

import com.example.coupon.model.Coupon;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CouponRepository {

    private final ConcurrentHashMap<String, Coupon> store = new ConcurrentHashMap<>();


    public void save(Coupon coupon){
        store.put(coupon.getCode(), coupon);
    }

    public Optional<Coupon> findByCode(String code){
        return Optional.ofNullable(store.get(code));
    }


    public List<Coupon> findAll(){
        return new ArrayList<>(store.values());
    }


    public void deleteByCode(String code){
        store.remove(code);
    }

    public void clearAll(){
        store.clear();
    }
}
