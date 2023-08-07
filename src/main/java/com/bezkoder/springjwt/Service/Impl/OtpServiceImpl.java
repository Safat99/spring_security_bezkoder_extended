package com.bezkoder.springjwt.Service.Impl;

import com.bezkoder.springjwt.Service.OtpService;
import com.bezkoder.springjwt.models.EmailDetails;
import com.bezkoder.springjwt.models.OtpData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpServiceImpl implements OtpService {

    private final Map<String, OtpData> otpCache = new ConcurrentHashMap<>();
    private final EmailServiceImpl emailService;

    @Autowired
    public OtpServiceImpl(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @Override
    public String generateRandomOtp(int length) {
        String numbers = "0123456789";
        Random randomObject = new Random();
        char[] otp = new char[length];
        for (int i=0; i<length; i++) {
            otp[i] = numbers.charAt(randomObject.nextInt(10)); // cast to character
        }

        return new String(otp);
    }

    @Override
    public boolean validateOtp(String email, String otp) {
        OtpData otpData = otpCache.get(email);
        if (otpData != null && Objects.equals(otpData.getOtp(), otp)) {
            if (System.currentTimeMillis() - otpData.getTimestamp() <= 300000) {
                otpCache.remove(email);
                return true;
            }
        }
        return false;
    }

    @Override
    public ResponseEntity<?> sendOtp(String email, String otp) {
        otpCache.put(email, new OtpData(otp, System.currentTimeMillis()));
        String text = "Hey! \n Your Otp for Registration is " + otp + "\n This otp will be invalid after 5 minutes. \nRegards,\n Safat";

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(email);
        emailDetails.setMsgBody(text);
        emailDetails.setSubject("Email verification from Real Madrid");

        return emailService.sendSimpleMail(emailDetails);
    }
}
