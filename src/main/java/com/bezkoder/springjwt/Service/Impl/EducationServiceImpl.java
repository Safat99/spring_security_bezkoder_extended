package com.bezkoder.springjwt.Service.Impl;

import com.bezkoder.springjwt.Service.EducationService;
import com.bezkoder.springjwt.exception.BadRequestException;
import com.bezkoder.springjwt.exception.ResourceNotFoundException;
import com.bezkoder.springjwt.models.DegreeName;
import com.bezkoder.springjwt.models.Education;
import com.bezkoder.springjwt.models.FileExtension;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.response.GetEducationResponse;
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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EducationServiceImpl implements EducationService {
    UserRepository userRepository;
    EducationRepository educationRepository;

    @Autowired
    public EducationServiceImpl(UserRepository userRepository,
                                EducationRepository educationRepository) {
        this.userRepository = userRepository;
        this.educationRepository = educationRepository;
    }

    @Override
    public void save(MultipartFile file, DegreeName degreeName, Float grade, Integer passingYear, Long userId)
            throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("Error: User is not found."));
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty or not provided.");
        }

        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/uploads", fileName);
        if (Objects.isNull(fileName))
            throw new RuntimeException("file name is null");
        String fileExtension = getFileExtension(fileName);
        if (!FileExtension.isValid(fileExtension)) {
            throw new BadRequestException("Error: file Extension is not valid!!");
        }

        try {
            Files.write(filePath, file.getBytes());
        } catch (Exception e) {
            throw new BadRequestException("File write fail.");
        }

        Education education = new Education();
        education.setGrade(grade);
        education.setDegreeName(degreeName);
        education.setPassingYear(passingYear);
        education.setUser(user);
        education.setCertificateUrl(filePath.toString());
        educationRepository.save(education);
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        String fileExtension = "";
        if (dotIndex != -1) {
            fileExtension = fileName.substring(dotIndex + 1).toLowerCase();
        }
        return fileExtension;
    }

    @Override
    public List<GetEducationResponse> getUserEducationInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Error: User is not found."));
        List<Education> educationList = educationRepository.findEducationByUserId(id);

        return educationList.stream()
                .map(education -> GetEducationResponse.builder()
                        .id(education.getId())
                        .username(education.getUser().getUsername())
                        .degreeName(education.getDegreeName())
                        .passingYear(education.getPassingYear())
                        .certificateUrl(education.getCertificateUrl())
                        .build())
                .collect(Collectors.toList());
    }
}
