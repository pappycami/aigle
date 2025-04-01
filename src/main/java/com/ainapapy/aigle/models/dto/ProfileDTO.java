package com.ainapapy.aigle.models.dto;

import java.time.LocalDate;

public class ProfileDTO {
    private Long id;
    private String phone;
    private String adress;
    private LocalDate birthDate;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAdress() { return adress; }
    public void setAdress(String adress) { this.adress = adress; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
}
