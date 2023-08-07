package com.bezkoder.springjwt.payload.request;


import lombok.Data;

@Data
public class ValidateUserRequest {

    private String email;
    private String otp;
}
