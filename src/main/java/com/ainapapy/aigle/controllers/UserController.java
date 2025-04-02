package com.ainapapy.aigle.controllers;

import com.ainapapy.aigle.models.User;
import com.ainapapy.aigle.models.dto.UserDTO;
import com.ainapapy.aigle.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(userService::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(u -> ResponseEntity.ok(userService.convertToDTO(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        User user = userService.convertToEntity(userDTO);
        return userService.convertToDTO(userService.saveUser(user));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEmail(dto.getEmail());
            user.setPassword(dto.getPassword());
            user.setRole(userService.convertToRoleEnum(dto.getRole()));
            return ResponseEntity.ok(userService.convertToDTO(userService.saveUser(user)));
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> partialUpdateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (dto.getEmail() != null) user.setEmail(dto.getEmail());
            if (dto.getPassword() != null) user.setPassword(dto.getPassword());
            if (dto.getRole() != null) user.setRole(userService.convertToRoleEnum(dto.getRole()));
            return ResponseEntity.ok(userService.convertToDTO(userService.saveUser(user)));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted!");
    }
}
