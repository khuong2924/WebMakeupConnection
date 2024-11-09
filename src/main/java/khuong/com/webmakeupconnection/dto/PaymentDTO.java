package khuong.com.webmakeupconnection.dto;

import khuong.com.webmakeupconnection.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    private Long UserID;
    private BigDecimal amount;
    private String status;
    private LocalDateTime createdAt;
}