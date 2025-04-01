package com.ainapapy.aigle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ainapapy.aigle.models.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
}
