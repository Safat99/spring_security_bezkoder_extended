package com.bezkoder.springjwt.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "education")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private DegreeName degreeName;

    private Integer passingYear;

    private String certificateUrl;

    private Float grade;

}
