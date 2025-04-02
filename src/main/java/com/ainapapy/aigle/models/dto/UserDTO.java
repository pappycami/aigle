package com.ainapapy.aigle.models.dto;

import com.ainapapy.aigle.models.dto.validations.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private Long id;
    
    @NotNull(groups = ValidationGroups.OnPut.class)
    private String email;
    
    @NotNull(groups = ValidationGroups.OnPut.class)
    private String role;

    @NotNull(groups = ValidationGroups.OnPatch.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(required = false)
    private ProfileDTO profile;
    
    @JsonProperty(required = false)
    private List<GroupDTO> groups;
}
