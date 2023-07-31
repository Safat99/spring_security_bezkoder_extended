package com.bezkoder.springjwt.payload.response;

import com.bezkoder.springjwt.models.DegreeName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetEducationResponse {

    private Long id;
    private String username;
    private DegreeName degreeName;
    private Integer passingYear;
    private String certificateUrl;

}
