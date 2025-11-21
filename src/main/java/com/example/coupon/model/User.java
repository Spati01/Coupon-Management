package com.example.coupon.model;

import com.example.coupon.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String email;
    private String password;
    private Role role;
}
