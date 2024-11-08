package khuong.com.webmakeupconnection.dto;

import khuong.com.webmakeupconnection.entity.Statistic.Period;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StatisticDTO {
    private Long id;
    private Long userId;
    private Double income;
    private Double expenses;
    private String serviceType;
    private String location;
    private Period period;
    private LocalDateTime createdAt;
}