package khuong.com.webmakeupconnection.dto;

import khuong.com.webmakeupconnection.entity.Profile.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
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