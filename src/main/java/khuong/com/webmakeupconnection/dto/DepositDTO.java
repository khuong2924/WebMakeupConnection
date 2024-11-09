package khuong.com.webmakeupconnection.dto;

import khuong.com.webmakeupconnection.entity.Deposit.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositDTO {
    private Long id;
    private Long paymentId;
    private Double amount;
    private LocalDateTime depositDate;
    private Status status;
}