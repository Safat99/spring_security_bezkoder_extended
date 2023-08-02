package com.bezkoder.springjwt.payload.request;

import com.bezkoder.springjwt.models.DegreeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AdminUserEducationRequest {

    private DegreeName degreeName;
    private Float grade;
    private Integer passingYear;
    private Long userId;
}
