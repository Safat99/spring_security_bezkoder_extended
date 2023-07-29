package com.bezkoder.springjwt.Service.Impl;

import com.bezkoder.springjwt.Service.AdminService;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.response.BirthdayRangeResponse;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final BirthdayRangeResponse resp;

    public AdminServiceImpl(UserRepository userRepository, BirthdayRangeResponse resp) {
        this.userRepository = userRepository;
        this.resp = resp;
    }

    public List<BirthdayRangeResponse> getUserBetweenBirthdate(Date startDate, Date endDate) {
        List<User> users = userRepository.findByBirthdateBetween(startDate, endDate);

//        List<BirthdayRangeResponse> customDtos = new ArrayList<BirthdayRangeResponse>();
//
//        for (User u: users) {
//            customDtos.add(resp.convertCustomDto(u));
//        }

        List<BirthdayRangeResponse> customDtos = users.stream()
                                        .map(resp::convertCustomDto)
                                        .toList();


        return customDtos;
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
