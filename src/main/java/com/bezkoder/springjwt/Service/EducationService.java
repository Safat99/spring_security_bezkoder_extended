package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.DegreeName;
import com.bezkoder.springjwt.models.Education;
import com.bezkoder.springjwt.payload.response.GetEducationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EducationService {

//    public void init();
    void save(MultipartFile file, DegreeName degreeName, Float grade, Integer passingYear, Long userId) throws IOException;

    List<GetEducationResponse> getUserEducationInfo(Long id);
}
