package com.example.coupon.exception;

public class ApiException  extends RuntimeException{

    public ApiException(String msg){
        super(msg);
    }

    public ApiException(String msg, Throwable t){
        super(msg, t);
    }
}
