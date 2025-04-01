package com.ainapapy.aigle.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "profils")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    private String adress;
    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Profile(Long id, String phone, String adress, LocalDate birthDate, User user) {
        this.id = id;
        this.phone = phone;
        this.adress = adress;
        this.birthDate = birthDate;
        this.user = user;
    }

    public Profile() {
    }
    
    
}

