package com.bezkoder.springjwt.payload.request;

import java.util.Date;
import java.util.Set;

import com.bezkoder.springjwt.models.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email(message = "must be a valid email")
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
  @Pattern(regexp = "\\d{7,11}", message = "phone Number is not okay")
  private String phoneNumber;

  public User convertToUserEntity(SignupRequest signupRequest) {
    User user = new User();

    user.setUsername(signupRequest.getUsername());
    user.setEmail(signupRequest.getEmail());
    user.setPassword(signupRequest.getPassword());
    user.setFirstname(signupRequest.getFirstname());
    user.setLastname(signupRequest.getLastname());
    user.setBirthdate(signupRequest.getBirthdate());
    user.setPhoneNumber(signupRequest.getPhoneNumber());

    return user;
//    return User.builder()
//            .username(signupRequest.getUsername())
//            .email(signupRequest.getEmail())
//            .firstname(signupRequest.getFirstname())
//            .lastname(signupRequest.getLastname())
//            .birthdate(signupRequest.getBirthdate())
//            .password(encoder.encode(signupRequest.getPassword()) )
//            .build();
  }
}
