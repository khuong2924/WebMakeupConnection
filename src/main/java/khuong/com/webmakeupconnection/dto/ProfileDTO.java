package khuong.com.webmakeupconnection.dto;

import khuong.com.webmakeupconnection.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private Long id;
    private Long user_id;
    private String fullName;
    private String birthDate;
    private String gender;
    private String bio;
    private String address;
    private String portfolioPhoto;
    private String coverPhoto;
    private String role;
}