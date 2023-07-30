package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    public ResponseEntity<?> registerUser(SignupRequest signupRequest);

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
}
