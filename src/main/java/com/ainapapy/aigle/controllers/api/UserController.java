package com.ainapapy.aigle.controllers.api;

import com.ainapapy.aigle.models.dto.UserDTO;
import com.ainapapy.aigle.models.dto.validations.ValidationGroups;
import com.ainapapy.aigle.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
                .orElseGet(() -> ResponseEntity.notFound().build());
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
        if ( userPuted != null) {
            return ResponseEntity.ok(userPuted);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> partialUpdateUser(
            @PathVariable Long id, 
            @Validated(ValidationGroups.OnPatch.class) @RequestBody UserDTO dto) 
    {
        UserDTO userPatched = userService.PatchUser(id, dto);
        if ( userPatched != null) {
            return ResponseEntity.ok(userPatched);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted!");
    }
}
