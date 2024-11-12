package khuong.com.webmakeupconnection.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profiles")
@Data

public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "user_id", nullable = false)
//    private Long userId;
    @OneToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    private String fullName;

    private String birthDate;

    private String gender;

    private String bio;

    private String address;

    private String portfolioPhoto;

    private String coverPhoto;


    public Long getUser_id() {
        return this.user.getId();
    }

    public void setUser_id(Long user_id) {
        this.user.setId(user_id);
    }

}
