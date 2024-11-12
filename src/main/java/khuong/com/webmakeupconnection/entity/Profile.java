package khuong.com.webmakeupconnection.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "user_id", nullable = false)
//    private Long userId;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
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
