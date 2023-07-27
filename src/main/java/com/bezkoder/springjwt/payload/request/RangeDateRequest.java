package com.bezkoder.springjwt.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;


@Data
public class RangeDateRequest {

//    @NotBlank
    private Date startDate;
//    @NotBlank
    private Date endDate;
}