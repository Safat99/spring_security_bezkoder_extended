package com.bezkoder.springjwt.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email") 
    })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @NotBlank
  @Size(max = 20)
  private String firstname;
  @NotBlank
  @Size(max = 20)
  private String lastname;

  private Date birthdate;

  private Boolean isActive;

  @NotBlank
  private String phoneNumber;

  public User() {
  }

  public User(String username, String email, String password, String firstname, String lastname, Date birthdate, String phoneNumber) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.firstname = firstname;
    this.lastname = lastname;
    this.birthdate = birthdate;
    this.phoneNumber = phoneNumber;
    this.isActive = true;
  }

  public void setActive(Boolean active) {
    this.isActive = active;
  }
}
