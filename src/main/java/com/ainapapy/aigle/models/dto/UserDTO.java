package com.ainapapy.aigle.models.dto;

import java.util.List;

public class UserDTO {
    private Long id;
    private String email;
    private String role;
    private ProfileDTO profile;
    private List<GroupDTO> groups;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public ProfileDTO getProfile() { return profile; }
    public void setProfile(ProfileDTO profile) { this.profile = profile; }

    public List<GroupDTO> getGroups() { return groups; }
    public void setGroups(List<GroupDTO> groups) { this.groups = groups; }
}
