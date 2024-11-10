package khuong.com.webmakeupconnection.dto;

import khuong.com.webmakeupconnection.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String role;
    private String cccd;
    private String portraitPhoto;
    LocalDateTime createdAt;
}
