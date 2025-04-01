package com.ainapapy.aigle.services;

import com.ainapapy.aigle.models.Group;
import com.ainapapy.aigle.models.Profile;
import com.ainapapy.aigle.models.User;
import com.ainapapy.aigle.models.dto.GroupDTO;
import com.ainapapy.aigle.models.dto.ProfileDTO;
import com.ainapapy.aigle.models.dto.UserDTO;
import com.ainapapy.aigle.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
        dto.setAdress(profile.getAdress());
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
        user.setRole(convertToRoleEnum(dto.getRole())); // Convertir String en Enum

        return user;
    }

    private User.roles convertToRoleEnum(String role) {
        try {
            return User.roles.valueOf(role.toUpperCase()); // Normalize to uppercase
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}
