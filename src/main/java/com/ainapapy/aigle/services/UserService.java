package com.ainapapy.aigle.services;

import com.ainapapy.aigle.models.User;
import com.ainapapy.aigle.models.convertors.UserConvertor;
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
        
    @Autowired
    private UserConvertor userConvertor;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public List<UserDTO> getAllUsersFormated() {
        return userRepository.findAll().stream()
                .map(userConvertor::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<UserDTO> getUserByIdFormated(Long id) {
        Optional<User> user = this.getUserById(id);
        return user.map(userConvertor::convertToDTO);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    public UserDTO userSaved(UserDTO userDTO) {
        User user = userConvertor.convertToEntity(userDTO);
        return userConvertor.convertToDTO(this.saveUser(user));
    }
    
    public UserDTO PutUser(Long id, UserDTO dto) {
        Optional<User> userOptional = this.getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEmail(dto.getEmail());
            user.setPassword(dto.getPassword());
            user.setRole(userConvertor.convertToRoleEnum(dto.getRole()));
            return userConvertor.convertToDTO(this.saveUser(user));
        }
        return null;
    }
    
    public UserDTO PatchUser(Long id, UserDTO dto) {
        Optional<User> userOptional = this.getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            if (dto.getEmail() != null) 
                user.setEmail(dto.getEmail());
            
            if (dto.getPassword() != null) 
                user.setPassword(dto.getPassword());
            
            if (dto.getRole() != null) 
                user.setRole(userConvertor.convertToRoleEnum(dto.getRole()));
            
            return userConvertor.convertToDTO(this.saveUser(user));
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
