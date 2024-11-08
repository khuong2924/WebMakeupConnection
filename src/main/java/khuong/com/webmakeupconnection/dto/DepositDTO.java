package khuong.com.webmakeupconnection.dto;

import khuong.com.webmakeupconnection.entity.Deposit.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DepositDTO {
    private Long id;
    private Long paymentId;
    private Double amount;
    private LocalDateTime depositDate;
    private Status status;
}