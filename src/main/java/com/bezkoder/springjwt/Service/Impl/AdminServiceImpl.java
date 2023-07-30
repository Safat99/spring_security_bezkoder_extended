package com.bezkoder.springjwt.Service.Impl;

import com.bezkoder.springjwt.Service.AdminService;
import com.bezkoder.springjwt.exception.ResourceNotFoundException;
import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.response.BirthdayRangeResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final BirthdayRangeResponse birthdayRangeResponse;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository, BirthdayRangeResponse birthdayRangeResponse) {
        this.userRepository = userRepository;
        this.birthdayRangeResponse = birthdayRangeResponse;
    }

    public List<BirthdayRangeResponse> getUserBetweenBirthdate(Date startDate, Date endDate) {
        List<User> users = userRepository.findByBirthdateBetween(startDate, endDate);
        return users.stream().map(birthdayRangeResponse::convertCustomDto).toList();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public ResponseEntity<?> deactivateUser(Long id) {
        Optional<User> userOptional = getUserById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            boolean hasAdminRole = user.getRoles().stream().anyMatch(role -> role.getName() == ERole.ROLE_ADMIN);

            if (hasAdminRole) {
                return ResponseEntity.badRequest().body(new MessageResponse("cannot deactivate another admin"));
            }
            user.setActive(false);
            // userRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
        } else {
            throw new ResourceNotFoundException("user not found!!");
        }
    }

    @Override
    public ResponseEntity<List<BirthdayRangeResponse>> getUsers(String startDate, String endDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<BirthdayRangeResponse> users = getUserBetweenBirthdate(dateFormat.parse(startDate), dateFormat.parse(endDate));
        return ResponseEntity.ok(users);
    }

}
