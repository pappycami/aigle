package com.ainapapy.aigle.controllers.api;

import com.ainapapy.aigle.exceptions.UserNotFoundException;
import com.ainapapy.aigle.models.dto.UserDTO;
import com.ainapapy.aigle.models.dto.validations.ValidationGroups;
import com.ainapapy.aigle.security.CustomUserDetails;
import com.ainapapy.aigle.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsersFormated();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<UserDTO> dto = userService.getUserByIdFormated(id);
        return dto.map(u -> ResponseEntity.ok(u))
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO newUser = userService.userSaved(userDTO);
        return ResponseEntity.ok(newUser);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id, 
            @Validated(ValidationGroups.OnPut.class) @RequestBody UserDTO dto) 
    {
        UserDTO userPuted = userService.PutUser(id, dto);
        return ResponseEntity.ok(userPuted);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> partialUpdateUser(
            @PathVariable Long id, 
            @Validated(ValidationGroups.OnPatch.class) @RequestBody UserDTO dto) 
    {
        UserDTO userPatched = userService.PatchUser(id, dto);
        return ResponseEntity.ok(userPatched);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id ) 
    {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted!");
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(Map.of(
            "username", user.getUsername(),
            "roles", user.getAuthorities()
        ));
    }
}
