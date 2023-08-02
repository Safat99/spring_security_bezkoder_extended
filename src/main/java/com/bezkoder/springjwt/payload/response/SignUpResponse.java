package com.bezkoder.springjwt.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpResponse {

    private String message;
    private Long userId;
}
