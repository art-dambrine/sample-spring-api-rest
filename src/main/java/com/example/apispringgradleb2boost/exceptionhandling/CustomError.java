package com.example.apispringgradleb2boost.exceptionhandling;

import lombok.Data;

@Data
public class CustomError {

    private int code;
    private String message;

    public CustomError(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
