package com.bezkoder.springjwt.Service.Impl;

import com.bezkoder.springjwt.Service.AuthService;
import com.bezkoder.springjwt.exception.BadRequestException;
import com.bezkoder.springjwt.exception.ResourceNotFoundException;
import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.bezkoder.springjwt.payload.response.SignUpResponse;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final OtpServiceImpl otpService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, JwtUtils jwtUtils, PasswordEncoder encoder, OtpServiceImpl otpService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
        this.otpService = otpService;
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signupRequest) {

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new BadRequestException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new BadRequestException("Error: Email is already in use!");
        }

        // Create new user's account
        User user = signupRequest.convertToUserEntity(signupRequest);
        user.setPassword(encoder.encode(user.getPassword()));
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
//            roles.add(userRole);
            throw new ResourceNotFoundException("Error: No role found for the user.");
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
                        roles.add(modRole);
                    }
                    case "user" -> {
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                    default -> throw new BadRequestException("Error: Invalid Role given!");
                }
            });
        }
        user.setRoles(roles);

        // check whether valid email and sent otp if everything okay
        String otp = otpService.generateRandomOtp(6);
        ResponseEntity<?> otpResponse = otpService.sendOtp(signupRequest.getEmail(), otp);

        if (otpResponse.getStatusCode().isError()) {
            return otpResponse;
        }

        userRepository.save(user);
        return otpResponse;

//        return ResponseEntity.ok(new SignUpResponse("User registered successfully!", user.getId()));
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {

        if (!Objects.equals(loginRequest.getPassword(), loginRequest.getConfirmPassword())) {
            throw new BadRequestException("password and confirm password field must be the same");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @Override
    public ResponseEntity<?> verifyUser(String email, String otp) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("email not exists"));

        boolean otpVerified = otpService.validateOtp(email, otp);
        if (!otpVerified) {
           throw new ResourceNotFoundException("otp not matched");
        }

        user.setVerified(true);
        userRepository.save(user);

        return ResponseEntity.ok("user verified and registered successfully");
    }


}
