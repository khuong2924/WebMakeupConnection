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
@Table(name = "statistics")
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Double income = 0.0;
    private Double expenses = 0.0;

    @Column(name = "service_type", length = 50)
    private String serviceType;

    @Column(length = 255)
    private String location;

    @Enumerated(EnumType.STRING)
    private Period period;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Period {
        Weekly, Monthly, Yearly
    }

}
