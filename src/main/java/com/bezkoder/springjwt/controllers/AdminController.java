package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.AdminService;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.RangeDateRequest;
import com.bezkoder.springjwt.payload.response.BirthdayRangeResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping("/get-user-by-birthdate")
    public ResponseEntity<List<BirthdayRangeResponse>> getUsers(@Valid @RequestBody RangeDateRequest rangeDate) {
        List<BirthdayRangeResponse> users = adminService.getUserBetweenBirthdate(rangeDate.getStartDate(), rangeDate.getEndDate() );
        return ResponseEntity.ok(users);
    }

    @Transactional
    @PutMapping("/deactivate-user/{id}")
    public ResponseEntity<?> deactivateUser(@PathVariable Long id) {
        Optional<User> userOptional = adminService.getUserById(id);

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