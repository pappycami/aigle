package com.ainapapy.aigle.models.dto;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    
    private Long id;
    
    private String firstname;
    
    private String lastname;
    
    private String phone;
    
    private String address;
    
    private LocalDate birthDate;
}
