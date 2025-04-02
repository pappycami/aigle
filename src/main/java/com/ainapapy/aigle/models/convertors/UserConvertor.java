package com.ainapapy.aigle.models.convertors;

import com.ainapapy.aigle.models.Group;
import com.ainapapy.aigle.models.Profile;
import com.ainapapy.aigle.models.User;
import com.ainapapy.aigle.models.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConvertor {
    
    @Autowired
    private ProfileConvertor profileConvertor;
    
    @Autowired
    private GroupConvertor groupConvertor;
    
    // Convertir UserDTO en User (utile pour la création et mise à jour)
    public User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(convertToRoleEnum(dto.getRole())); // Convertir String en Enum

        if (dto.getProfile() != null) {
            Profile profile = new Profile();
            profile.setFirstname(dto.getProfile().getFirstname());
            profile.setLastname(dto.getProfile().getLastname());
            profile.setPhone(dto.getProfile().getPhone());
            profile.setAddress(dto.getProfile().getAddress());
            profile.setBirthDate(dto.getProfile().getBirthDate());
            profile.setUser(user);
            user.setProfile(profile);
        }

        if (dto.getGroups() != null) {
            Set<Group> groups = dto.getGroups().stream().map(groupDTO -> {
                Group group = new Group();
                group.setName(groupDTO.getName());
                group.setDescription(groupDTO.getDescription());
                // Save the group to the database if necessary
                return groupConvertor.saveOrUpdateGroup(group);
            }).collect(Collectors.toSet());
            user.setGroups(groups);
        }
        return user;
    }
    
    // Convertir User en UserDTO
    public UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().toString());

        if (user.getProfile() != null) {
            dto.setProfile(profileConvertor.convertToDTO(user.getProfile()));
        }

        if (user.getGroups() != null) {
            dto.setGroups(user.getGroups().stream().map(groupConvertor::convertToDTO).collect(Collectors.toList()));
        }
        return dto;
    }
    
    
    
    public User.roles convertToRoleEnum(String role) {
        try {
            return User.roles.valueOf(role.toUpperCase()); // Normalize to uppercase
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
    
}
