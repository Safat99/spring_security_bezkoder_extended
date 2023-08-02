package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.AdminService;
import com.bezkoder.springjwt.Service.EducationService;
import com.bezkoder.springjwt.models.DegreeName;
import com.bezkoder.springjwt.payload.request.AdminUserEducationRequest;
import com.bezkoder.springjwt.payload.response.BirthdayRangeResponse;
import com.bezkoder.springjwt.payload.response.GetEducationResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;
    private final EducationService educationService;

    @Autowired
    public AdminController(AdminService adminService, EducationService educationService) {
        this.adminService = adminService;
        this.educationService = educationService;
    }

    @GetMapping("/get-user-by-birthdate")
    public ResponseEntity<List<BirthdayRangeResponse>> getUsers(@RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate) throws ParseException {
        return adminService.getUsers(startDate, endDate);
    }

    @Transactional
    @PatchMapping("/deactivate-user/{id}")
    public ResponseEntity<?> deactivateUser(@PathVariable Long id) {
        return adminService.deactivateUser(id);
    }

    @GetMapping("/get-user-education-info/{id}")
    public ResponseEntity<List<GetEducationResponse>> getUserEducationInfo(@PathVariable Long id) {
        List<GetEducationResponse> educations = educationService.getUserEducationInfo(id);
        return ResponseEntity.ok(educations);
    }

    @PatchMapping(value = "/education", consumes = { MediaType.APPLICATION_JSON_VALUE,
                                                     MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> insertEducationInfo(
            @RequestPart("education_info") AdminUserEducationRequest request,
            @RequestParam("file") MultipartFile file
            ) {
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
