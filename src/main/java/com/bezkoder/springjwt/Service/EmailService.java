package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.EmailDetails;
import org.springframework.http.ResponseEntity;

public interface EmailService {

    ResponseEntity<?> sendSimpleMail(EmailDetails emailDetails);
}
