package com.bezkoder.springjwt.Service.Impl;

import com.bezkoder.springjwt.Service.EducationService;
import com.bezkoder.springjwt.exception.BadRequestException;
import com.bezkoder.springjwt.exception.ResourceNotFoundException;
import com.bezkoder.springjwt.models.DegreeName;
import com.bezkoder.springjwt.models.Education;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.EducationRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class EducationServiceImpl implements EducationService {

    private final Path root = Paths.get("uploads");

    @Autowired
    UserRepository userRepository;

    @Autowired
    EducationRepository educationRepository;

    @Override
    public void save(MultipartFile file, DegreeName degreeName, Float grade, Integer passingYear, Long userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Error: User is not found."));


        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty or not provided.");
        }

        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get("src/main/resources/static/uploads", fileName);

        try {
            Files.write(filePath, file.getBytes());
//            Files.copy(file.getInputStream(), this.root.resolve(Objects.requireNonNull(file.getOriginalFilename())) );
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new BadRequestException("A file of that name already exists.");
            }
            throw new BadRequestException(e.getMessage());
        }

        Education education = new Education();
        education.setGrade(grade);
        education.setDegreeName(degreeName);
        education.setPassingYear(passingYear);
        education.setUser(user);
        education.setCertificateUrl(fileName);

        educationRepository.save(education);

    }
}
