package khuong.com.webmakeupconnection.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPostDTO {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private String jobType;
    private String location;
    private Double salary;
    private String skillRequirements;
    private String status;
    private LocalDateTime createdAt;
}