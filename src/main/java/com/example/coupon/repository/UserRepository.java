package com.example.coupon.repository;


import com.example.coupon.model.User;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {

    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();


    public User save(User user) {
        users.put(user.getEmail(), user);
        return user;
    }

    public boolean existsByEmail(String email) {
        return users.containsKey(email);
    }
}
