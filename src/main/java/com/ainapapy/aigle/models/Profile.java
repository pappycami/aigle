package com.ainapapy.aigle.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "profils")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String firstname;
    
    @Column(nullable = true)
    private String lastname;
    
    @Column(nullable = true)
    private String phone;
    
    @Column(nullable = true)
    private String address;
    
    @Column(nullable = true)
    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}

