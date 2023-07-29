package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.response.BirthdayRangeResponse;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AdminService {

    List<BirthdayRangeResponse> getUserBetweenBirthdate(Date startDate, Date endDate);
    Optional<User> getUserById(Long userId);
}