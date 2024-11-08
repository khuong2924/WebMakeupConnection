package khuong.com.webmakeupconnection.dto;

import khuong.com.webmakeupconnection.entity.JobPost.JobType;
import khuong.com.webmakeupconnection.entity.JobPost.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobPostDTO {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private JobType jobType;
    private String location;
    private Double salary;
    private String skillRequirements;
    private Status status;
    private LocalDateTime createdAt;
}