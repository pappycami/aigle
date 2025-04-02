package com.ainapapy.aigle.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class UserDTO {
    private Long id;
    private String email;
    private String role;

    // Only allow writing (POST, PUT, PATCH)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(required = false)
    private ProfileDTO profile;
    
    @JsonProperty(required = false)
    private List<GroupDTO> groups;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
 
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public ProfileDTO getProfile() { return profile; }
    public void setProfile(ProfileDTO profile) { this.profile = profile; }

    public List<GroupDTO> getGroups() { return groups; }
    public void setGroups(List<GroupDTO> groups) { this.groups = groups; }
}
