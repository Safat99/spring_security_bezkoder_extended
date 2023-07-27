package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.AdminService;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.RangeDateRequest;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/getUsersByBirthdate")
    public ResponseEntity<List<User>> getUsers(@Valid @RequestBody RangeDateRequest rangeDate) {

        List<User> users = adminService.getUserBetweenBirthdate(rangeDate.getStartDate(), rangeDate.getEndDate() );
        return ResponseEntity.ok(users);
    }

    @Transactional
    @PutMapping("/deactivateUser/{userId}")
    public ResponseEntity<?> deactivateUser(@PathVariable Long userId) {

        Optional<User> userOptional = adminService.getUserById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActive(false);
            // userRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
