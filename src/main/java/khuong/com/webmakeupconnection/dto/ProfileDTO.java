package khuong.com.webmakeupconnection.dto;

import khuong.com.webmakeupconnection.entity.Profile.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileDTO {
    private Long id;
    private Long userId;
    private String fullName;
    private LocalDate birthDate;
    private Gender gender;
    private String bio;
    private String skills;
    private String experience;
    private String portfolioPhoto;
}