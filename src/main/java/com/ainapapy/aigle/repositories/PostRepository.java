package com.ainapapy.aigle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ainapapy.aigle.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
}