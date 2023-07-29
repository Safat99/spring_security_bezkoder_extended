package com.bezkoder.springjwt.payload.response;

import com.bezkoder.springjwt.models.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class BirthdayRangeResponse {

    private Long id;

    private String email;

    private String firstname;

    private String lastname;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthdate;

    public BirthdayRangeResponse convertCustomDto(User user) {

        BirthdayRangeResponse customUserDto = new BirthdayRangeResponse();

        customUserDto.setId(user.getId());
        customUserDto.setEmail(user.getEmail());
        customUserDto.setFirstname(user.getFirstname());
        customUserDto.setLastname(user.getLastname());
        customUserDto.setBirthdate(user.getBirthdate());

        return customUserDto;
    }

}

