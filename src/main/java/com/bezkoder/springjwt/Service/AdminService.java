package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.DegreeName;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.AdminUserEducationRequest;
import com.bezkoder.springjwt.payload.response.BirthdayRangeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<BirthdayRangeResponse> getUserBetweenBirthdate(Date startDate, Date endDate);

    Optional<User> getUserById(Long userId);

    ResponseEntity<?> deactivateUser(Long id);

    ResponseEntity<List<BirthdayRangeResponse>> getUsers(String startDate, String endDate) throws ParseException;

    ResponseEntity<?> insertEducationInfo(MultipartFile file, AdminUserEducationRequest request);
}