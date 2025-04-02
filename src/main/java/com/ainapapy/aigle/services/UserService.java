package com.ainapapy.aigle.services;

import com.ainapapy.aigle.models.Group;
import com.ainapapy.aigle.models.Profile;
import com.ainapapy.aigle.models.User;
import com.ainapapy.aigle.models.dto.GroupDTO;
import com.ainapapy.aigle.models.dto.ProfileDTO;
import com.ainapapy.aigle.models.dto.UserDTO;
import com.ainapapy.aigle.repositories.GroupRepository;
import com.ainapapy.aigle.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private GroupRepository groupRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Convertir User en UserDTO
    public UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().toString());

        if (user.getProfile() != null) {
            dto.setProfile(convertProfileToDTO(user.getProfile()));
        }

        if (user.getGroups() != null) {
            dto.setGroups(user.getGroups().stream().map(this::convertGroupToDTO).collect(Collectors.toList()));
        }

        return dto;
    }

    private ProfileDTO convertProfileToDTO(Profile profile) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(profile.getId());
        dto.setFirstname(profile.getFirstname());
        dto.setLastname(profile.getLastname());
        dto.setAddress(profile.getAddress());
        dto.setPhone(profile.getPhone());
        dto.setBirthDate(profile.getBirthDate());
        return dto;
    }

    private GroupDTO convertGroupToDTO(Group group) {
        GroupDTO dto = new GroupDTO();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setDescription(group.getDescription());
        return dto;
    }

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
                return saveOrUpdateGroup(group);
            }).collect(Collectors.toSet());
            user.setGroups(groups);
        }

        return user;
    }

    public User.roles convertToRoleEnum(String role) {
        try {
            return User.roles.valueOf(role.toUpperCase()); // Normalize to uppercase
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
    
    private Group saveOrUpdateGroup(Group group) {
        // Check if the group already exists in the database
        Optional<Group> existingGroup = groupRepository.findByName(group.getName());
        if (existingGroup.isPresent()) {
            return existingGroup.get(); // Return the existing group
        }
        return groupRepository.save(group); // Save the new group
    }
}
