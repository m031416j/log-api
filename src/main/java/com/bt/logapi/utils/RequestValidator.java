package com.bt.logapi.utils;

public class RequestValidator {

    private RequestValidator(){}


    public static void isValidId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Application id is null");
        }
    }
}
