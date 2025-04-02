package com.ainapapy.aigle.models.convertors;

import com.ainapapy.aigle.models.Profile;
import com.ainapapy.aigle.models.dto.ProfileDTO;
import org.springframework.stereotype.Component;

@Component
public class ProfileConvertor {
    
    public ProfileDTO convertToDTO(Profile profile) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(profile.getId());
        dto.setFirstname(profile.getFirstname());
        dto.setLastname(profile.getLastname());
        dto.setAddress(profile.getAddress());
        dto.setPhone(profile.getPhone());
        dto.setBirthDate(profile.getBirthDate());
        return dto;
    }
    
}
