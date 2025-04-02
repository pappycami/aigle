package com.ainapapy.aigle.services;

import com.ainapapy.aigle.models.Profile;
import com.ainapapy.aigle.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository ProfileRepository;

    public List<Profile> getAllProfiles() {
        return ProfileRepository.findAll();
    }

    public Optional<Profile> getProfileById(Long id) {
        return ProfileRepository.findById(id);
    }

    public Profile saveProfile(Profile Profile) {
        return ProfileRepository.save(Profile);
    }

    public void deleteProfile(Long id) {
        ProfileRepository.deleteById(id);
    }
}
