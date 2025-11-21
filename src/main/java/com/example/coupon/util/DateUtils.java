package com.example.coupon.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateUtils {
    public static LocalDateTime now(){
        return LocalDateTime.now(ZoneOffset.systemDefault());
    }
}
