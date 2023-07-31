package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.DegreeName;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EducationService {

//    public void init();
    void save(MultipartFile file, DegreeName degreeName, Float grade, Integer passingYear, Long userId) throws IOException;
}
