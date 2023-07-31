package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.EducationService;
import com.bezkoder.springjwt.models.DegreeName;
import com.bezkoder.springjwt.models.Education;
import com.bezkoder.springjwt.payload.response.GetEducationResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private EducationService educationService;

    @PostMapping("/education")
    public ResponseEntity<?> insertEducationInfo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("degree_name") DegreeName degreeName,
            @RequestParam("grade") Float grade,
            @RequestParam("passing_year") Integer passingYear,
            @RequestParam("user_id") Long userId
    ) {
        String message;
        try {
            educationService.save(file, degreeName, grade, passingYear, userId);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));

        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.badRequest().body(new MessageResponse(message));
        }
    }

    @GetMapping("/get-user-education-info/{id}")
    public ResponseEntity<List<GetEducationResponse>> getUserEducationInfo(@PathVariable Long id) {
        List<GetEducationResponse> educations = educationService.getUserEducationInfo(id);
        return ResponseEntity.ok(educations);
    }

}
