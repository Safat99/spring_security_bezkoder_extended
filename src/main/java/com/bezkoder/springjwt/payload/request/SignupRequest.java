package com.bezkoder.springjwt.payload.request;

import java.util.Date;
import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequest {
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  private Set<String> role;

  @NotBlank
  @Size(min = 6, max = 40)
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,40}$",
          message = "password must contains 1 small, 1 caps, 1 digit and 1 special character")
  private String password;

  @NotBlank
  private String firstname;
  @NotBlank
  private String lastname;

  @NotNull
  private Date birthdate;

  @NotBlank
  @Pattern(regexp = "\\d{7,11}", message = "phone Number ")
  private String phoneNumber;
}
