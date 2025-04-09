package com.ainapapy.aigle.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private Long id;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    private String email;
    
    @NotBlank(message = "Rôle requis")
    private String role;

    @NotBlank(message = "Le motDePasse est obligatoire")
    @Size(min = 6, message = "Minimum 6 caractères")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(required = false)
    private ProfileDTO profile;
    
    @JsonProperty(required = false)
    private List<GroupDTO> groups;
}
