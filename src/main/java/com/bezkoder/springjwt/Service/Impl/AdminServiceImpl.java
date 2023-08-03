package com.bezkoder.springjwt.Service.Impl;

import com.bezkoder.springjwt.Service.AdminService;
import com.bezkoder.springjwt.Service.EducationService;
import com.bezkoder.springjwt.exception.ResourceNotFoundException;
import com.bezkoder.springjwt.models.DegreeName;
import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.AdminUserEducationRequest;
import com.bezkoder.springjwt.payload.response.BirthdayRangeResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final BirthdayRangeResponse birthdayRangeResponse;
    private final EducationService educationService;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository, BirthdayRangeResponse birthdayRangeResponse, EducationService educationService) {
        this.userRepository = userRepository;
        this.birthdayRangeResponse = birthdayRangeResponse;
        this.educationService = educationService;
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

    @Override
    public ResponseEntity<?> insertEducationInfo(MultipartFile file, AdminUserEducationRequest request) {
        String message;
        try {
            educationService.save(file, request.getDegreeName(), request.getGrade(), request.getPassingYear(), request.getUserId());

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));

        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.badRequest().body(new MessageResponse(message));
        }
    }
    
}
