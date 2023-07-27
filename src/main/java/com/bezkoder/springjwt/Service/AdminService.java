package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUserBetweenBirthdate(Date startDate, Date endDate) {
        return userRepository.findByBirthdateBetween(startDate, endDate);
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
