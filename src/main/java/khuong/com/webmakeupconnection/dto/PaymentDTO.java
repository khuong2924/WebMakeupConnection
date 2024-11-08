package khuong.com.webmakeupconnection.dto;

import khuong.com.webmakeupconnection.entity.Payment.PaymentMethod;
import khuong.com.webmakeupconnection.entity.Payment.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private Long id;
    private Long userId;
    private Long jobPostId;
    private PaymentMethod paymentMethod;
    private Double amount;
    private Status status;
    private LocalDateTime createdAt;
}