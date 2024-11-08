package khuong.com.webmakeupconnection.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "deposits")
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    private Double amount;

    @Column(name = "deposit_date")
    private LocalDateTime depositDate;

    @Enumerated(EnumType.STRING)
    private Status status = Status.Held;

    public enum Status {
        Held, Released, Forfeited
    }

}
