package khuong.com.webmakeupconnection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositDTO {
    private Long id;
    private Long paymentId;
    private BigDecimal amount;
    private LocalDateTime depositDate;
    private String status;
}