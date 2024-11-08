package khuong.com.webmakeupconnection.dto;

import khuong.com.webmakeupconnection.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import khuong.com.webmakeupconnection.entity.User.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private Role role;
    private String portraitPhoto;
}
