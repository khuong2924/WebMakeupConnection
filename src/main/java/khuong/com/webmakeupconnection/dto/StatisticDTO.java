package khuong.com.webmakeupconnection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDTO {
    private Long id;
    private Long userId;
    private Double income;
    private Double expenses;
    private String serviceType;
    private String location;
    private String period;
    private LocalDateTime createdAt;
}