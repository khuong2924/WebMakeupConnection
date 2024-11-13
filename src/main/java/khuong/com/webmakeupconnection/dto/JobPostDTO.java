package khuong.com.webmakeupconnection.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private BigDecimal salary;
    private String postPhoto;
    private String status;
    private LocalDateTime createdAt;
}