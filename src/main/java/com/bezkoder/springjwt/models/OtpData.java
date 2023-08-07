package com.bezkoder.springjwt.models;

import lombok.Getter;

@Getter
public class OtpData {

    private final String otp;

    private final long timestamp;

    public OtpData(String otp, long timestamp) {
        this.otp = otp;
        this.timestamp = timestamp;
    }

}
